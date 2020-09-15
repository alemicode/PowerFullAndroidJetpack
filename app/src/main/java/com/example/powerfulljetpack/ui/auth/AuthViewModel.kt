package com.example.powerfulljetpack.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvi.main.util.ApiSuccessResponse
import com.example.mvi.main.util.GenericApiResponse
import com.example.powerfulljetpack.api.auth.network_responses.LoginResponse
import com.example.powerfulljetpack.api.auth.network_responses.RegistrationResponse
import com.example.powerfulljetpack.repository.auth.AuthRepository
import javax.inject.Inject

class AuthViewModel @Inject constructor(

    val authRepository: AuthRepository
) : ViewModel() {


    fun testLogin(): LiveData<GenericApiResponse<LoginResponse>> {

        return authRepository.loginTest(
            "mohamad.alemi.dev14@gmail.com",
            password = "55907099"
        )
    }

    //



    fun testRegistrarion(): LiveData<GenericApiResponse<RegistrationResponse>> {

        return authRepository.registerTest(
            "mohamad.alemi.dev14@gmail.com",
            "mohamad.alemi.dev14@gmail.com",
            "55907099",
            "55907099"

        )

    }

}