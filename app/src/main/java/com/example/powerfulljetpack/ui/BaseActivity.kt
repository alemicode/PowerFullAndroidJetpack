package com.example.powerfulljetpack.ui

import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.util.DataState
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), DataStateChangeListener {


    @Inject
    lateinit var sessionManager: SessionManager
    private val debug = "App Debug"


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
        }

    }


    abstract fun displayProgressBar(bool: Boolean)
}