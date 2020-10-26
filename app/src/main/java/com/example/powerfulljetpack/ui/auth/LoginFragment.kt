package com.example.powerfulljetpack.ui.auth


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.models.AuthToken
import com.example.powerfulljetpack.ui.auth.state.AuthStateEvent
import com.example.powerfulljetpack.ui.auth.state.AuthViewState
import com.example.powerfulljetpack.ui.auth.state.LoginFields
import kotlinx.android.synthetic.main.fragment_login.*



class LoginFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("debug : LoginFragment")


        login_button.setOnClickListener {
            login()

        }

        viewModel.viewState.observe(this, Observer { authViewState ->

            authViewState.LoginFields.let { loginFields ->

                loginFields?.login_email.let {

                    input_email.setText(it)
                }


                loginFields?.login_password.let {

                    input_password.setText(it)
                }

            }


        })

    }


    fun login() {

        viewModel.setStateEvent(
            AuthStateEvent.LoginAttemptEvent(
                input_email.text.toString(),
                input_password.text.toString()
            )

        )
    }

    /*
    * when click back it calls setLoginField which call live data observers
    *
    * */
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginField(
            LoginFields(
                input_email.text.toString(),
                input_password.text.toString()
            )
        )
    }
}

