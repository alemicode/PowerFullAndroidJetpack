package com.example.powerfulljetpack.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.mvi.main.util.ApiSuccessResponse
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.persistence.AppDatabase
import com.example.powerfulljetpack.persistence.AuthTokenDAO
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.BaseActivity
import com.example.powerfulljetpack.ui.ResponseType
import com.example.powerfulljetpack.ui.auth.state.AuthStateEvent
import com.example.powerfulljetpack.ui.auth.state.AuthViewState
import com.example.powerfulljetpack.ui.main.MainActivity
import com.example.powerfulljetpack.viewmodels.ViewModelProviderFactory
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity(),
    NavController.OnDestinationChangedListener {


    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth2)

        //call the interface when a page change
//        findNavController(R.id.nav_host_fragment_container).addOnDestinationChangedListener(this)

        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        subscribeObserver()

    }


    /*
    * check if token from user is not null jump login or registration field
    * */
    fun subscribeObserver() {


        viewModel.dataState.observe(
            this, Observer
            { datastate ->
                datastate.data?.let { data ->
                    data.data?.let { event ->


                        event.getContentIfNotHandled().let {

                            it?.authToken?.let {

                                println("debug : auth token is : ${it.token}")
                                viewModel.setAuthToken(it)
                            }
                            it?.LoginFields?.let {

                                println("debug : login ${it.login_email}")
                                viewModel.setLoginField(it)
                            }

                            it?.registrationField?.let {

                                println("registration field : ${it.registration_email}")
                                viewModel.setRegistrationFields(it)
                            }

                        }
                    }

                    data.response.let {
                        val event = it?.peekContent()
                        when (event?.responseType) {

                            is ResponseType.Toast -> {

                            }

                            is ResponseType.Dialog -> {

                            }

                            is ResponseType.None -> {
                                println("debug : AuthActivity : the message is ${event.message}")
                            }
                        }

                    }

                }


            })


        viewModel.viewState.observe(this, Observer
        {
            it.authToken?.let {

                sessionManager.login(it)
            }
        })

        sessionManager.cachedToken.observe(this, Observer
        { authToken ->

            if (authToken != null && authToken?.account_pk != -1 && authToken?.token != null) {

                navAuthActivity()
            }
        })
    }

    private fun navAuthActivity() {

        startActivity(Intent(this, MainActivity::class.java))

        //not back to previuse fragment/activity
        finish()

    }


    /*
    * this method calls when navcontroller change fragment or ...
    *
    */
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

        //when changing the page or fragment job should be cancelled
        viewModel.cancellActiveJob()
    }
}
