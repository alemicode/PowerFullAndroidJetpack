package com.example.powerfulljetpack.ui.auth


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.ui.auth.state.AuthStateEvent
import com.example.powerfulljetpack.ui.auth.state.RegistrationField

import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_register.*


/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("debug : RegisterFragment")

        assingIntoEditText()


        register_button.setOnClickListener {

            register()
        }


    }

    private fun assingIntoEditText() {
        /*
       * assing parameters into editText
       * */

        viewModel._viewState.observe(this, Observer { authViewState ->

            authViewState.registrationField?.let { registrationField ->

                registrationField.registration_email?.let {

                    input_email.setText(it)
                }

                registrationField.registration_username?.let {

                    input_username.setText(it)
                }

                registrationField.registration_password?.let {

                    input_password.setText(it)
                }

                registrationField.registration_confirm_password?.let {

                    input_password_confirm.setText(it)
                }
            }
        })

    }

    //

    fun register() {

        viewModel.setStateEvent(
            AuthStateEvent.RegisterAttemptEvent(
                input_email.text.toString(),
                input_username.text.toString(),
                input_password.text.toString(),
                input_password_confirm.text.toString()
            )

        )
    }

    /*
    * call viewState liveData to call assingIntoEditText() method in this Fragment
    * */
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setRegistrationFields(
            RegistrationField(
                input_email.text.toString(),
                input_username.text.toString(),
                input_password.text.toString(),
                input_password_confirm.text.toString()
            )
        )
    }
}



