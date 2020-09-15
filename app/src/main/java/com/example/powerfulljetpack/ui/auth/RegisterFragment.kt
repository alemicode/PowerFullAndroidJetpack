package com.example.powerfulljetpack.ui.auth


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.mvi.main.util.ApiEmptyResponse
import com.example.mvi.main.util.ApiErrorResponse
import com.example.mvi.main.util.ApiSuccessResponse
import com.example.powerfulljetpack.R


/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register,container,false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("debug : RegisterFragment")


        viewModel.testRegistrarion().observe(this, Observer {

            when (it) {
                is ApiSuccessResponse -> {

                    println("debug : ${it.body.email}")
                }

                is ApiErrorResponse -> {
                    println("debug : ${it.errorMessage}")

                }

                is ApiEmptyResponse -> {

                }
            }
        })


    }
}



