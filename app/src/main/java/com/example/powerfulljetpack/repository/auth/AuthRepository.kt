package com.example.powerfulljetpack.repository.auth

import androidx.lifecycle.LiveData
import com.example.mvi.main.util.GenericApiResponse
import com.example.powerfulljetpack.api.auth.OpenApiAuthService
import com.example.powerfulljetpack.api.auth.network_responses.LoginResponse
import com.example.powerfulljetpack.api.auth.network_responses.RegistrationResponse
import com.example.powerfulljetpack.persistence.AccountPropertiesDAO
import com.example.powerfulljetpack.persistence.AuthTokenDAO
import com.example.powerfulljetpack.session.SessionManager
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDAO,
    val accountPropertiesDao: AccountPropertiesDAO,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
) {

    fun loginTest(username: String, password: String): LiveData<GenericApiResponse<LoginResponse>> {

        return openApiAuthService.login(username,password)
    }


    fun registerTest(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<GenericApiResponse<RegistrationResponse>> {

        return openApiAuthService.register(email,username,password,confirmPassword)

    }
}