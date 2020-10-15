package com.example.powerfulljetpack.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.ui.BaseActivity
import com.example.powerfulljetpack.ui.auth.state.AuthStateEvent
import com.example.powerfulljetpack.ui.main.MainActivity
import com.example.powerfulljetpack.util.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import com.example.powerfulljetpack.viewmodels.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_auth2.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progress_bar
import javax.inject.Inject


class AuthActivity : BaseActivity(),
    NavController.OnDestinationChangedListener {
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        viewModel.cancellActiveJob()
    }


    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        findNavController(R.id.auth_nav_host_fragment).addOnDestinationChangedListener(this)

        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        checkPreviousAuthUser()

    }


    private fun subscribeObservers() {

        viewModel.dataState?.observe(this, Observer { dataState ->
            onDataStateChange(dataState)
            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        it.authToken?.let {
                            viewModel.setAuthToken(it)
                        }
                    }
                }
                data.response?.let { event ->
                    event.peekContent().let { response ->
                        response.message?.let { message ->
                            if (message.equals(RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE)) {
                                onFinishCheckPreviousAuthUser()
                            }
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(this, Observer {
            it.authToken?.let {
                sessionManager.login(it)
            }
        })

        sessionManager.cachedToken.observe(this, Observer { dataState ->
            dataState.let { authToken ->
                if (authToken != null && authToken.account_pk != -1 && authToken.token != null) {
                    navMainActivity()
                }
            }
        })
    }

    fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkPreviousAuthUser() {
        viewModel.setStateEvent(AuthStateEvent.CheckPerviuseAuthEvent())
    }

    private fun onFinishCheckPreviousAuthUser() {
        fragment_container.visibility = View.VISIBLE
    }

    override fun displayProgressBar(bool: Boolean) {
        if (bool) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }


    override fun hideSoftKeyboard() {

        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager
                .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)


        }
    }

}






