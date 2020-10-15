package com.example.powerfulljetpack.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.util.DataState
import com.example.powerfulljetpack.util.LastApiReponseResult
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), DataStateChangeListener {


    @Inject
    lateinit var sessionManager: SessionManager
    private val debug = "App Debug"

    lateinit var lastApiReponseResult: LastApiReponseResult

    /*
    * calling this interface just after getting response from dataState Observer
    * to handle specific state (loading, getting data, display error...)
    *
    * */
    override fun onDataStateChange(dataState: DataState<*>?) {

        dataState.let { dataState ->

            GlobalScope.launch(Main) {

                displayProgressBar(dataState?.loading!!.isLoading)

                dataState.error.let { errorEvent ->

                    handleStateError(errorEvent)

                }

                dataState.data.let { data ->

                    data?.response.let { responseEvent ->

                        handleStateResponse(responseEvent)

                    }
                }


            }
        }

    }


    private fun handleStateResponse(event: Event<Response>?) {

        event?.getContentIfNotHandled().let {

            when (it?.responseType) {

                is ResponseType.Toast -> {

                    it.message?.let {

                        displayToast(it)
                    }
                }

                is ResponseType.Dialog -> {

                    it.message.let {
                        displayErrorDialog(it)
                    }
                }

                is ResponseType.None -> {

                    println("debug : no response")
                }

                else -> println("debug : no response")

            }

            //notify state of state into subclasses

            it?.message?.let { it1 ->
                lastApiReponseResult = notifyStateTypeToSubClasses(0, it1)
            }
        }

    }


    private fun handleStateError(errorEvent: Event<StateError>?) {

        errorEvent?.getContentIfNotHandled().let {


            when (it?.response?.responseType) {


                is ResponseType.Toast -> {

                    it.response.message?.let {

                        displayToast(it)
                    }
                }

                is ResponseType.Dialog -> {

                    it.response.message.let {
                        displayErrorDialog(it)
                    }
                }

                is ResponseType.None -> {

                    println("debug : no response")
                }

                else -> println("debug : no response")

            }

            //notify state of state into fragment

            it?.response?.message?.let { it1 ->
                lastApiReponseResult = notifyStateTypeToSubClasses(1, it1)
            }

        }

    }

    /*
    * notyfy other subclasses like fragments that their responses are fail or success
    * type = 0 No Error
    * type = 1 Error
    * */
    fun notifyStateTypeToSubClasses(type: Int, message: String): LastApiReponseResult {
        return LastApiReponseResult(
            message,
            type
        )
    }

    abstract fun displayProgressBar(bool: Boolean)
    abstract fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    )
}