package com.example.powerfulljetpack.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.mvi.main.util.ApiEmptyResponse
import com.example.mvi.main.util.ApiErrorResponse
import com.example.mvi.main.util.ApiSuccessResponse
import com.example.mvi.main.util.GenericApiResponse
import com.example.powerfulljetpack.api.auth.OpenApiAuthService
import com.example.powerfulljetpack.api.auth.network_responses.LoginResponse
import com.example.powerfulljetpack.api.auth.network_responses.RegistrationResponse
import com.example.powerfulljetpack.models.AuthToken
import com.example.powerfulljetpack.persistence.AccountPropertiesDAO
import com.example.powerfulljetpack.persistence.AuthTokenDAO
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.Data
import com.example.powerfulljetpack.ui.Response
import com.example.powerfulljetpack.ui.ResponseType
import com.example.powerfulljetpack.ui.auth.state.AuthViewState
import com.example.powerfulljetpack.util.DataState
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDAO,
    val accountPropertiesDao: AccountPropertiesDAO,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
) {

    fun login(username: String, password: String): LiveData<DataState<AuthViewState>> {

        return openApiAuthService.login(username, password)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (response) {
                            is ApiSuccessResponse -> {

                                value = DataState.data(
                                    AuthViewState(

                                        authToken = AuthToken(
                                            account_pk = response.body.pk,
                                            token = response.body
                                                .token
                                        )

                                    ),
                                    response = null

                                )
                            }

                            is ApiErrorResponse -> {

                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse -> {


                            }

                        }
                    }
                }
            }
    }


    fun registration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>> {

        return openApiAuthService.register(email, username, password, confirmPassword)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {

                    override fun onActive() {
                        super.onActive()

                        when (response) {

                            is ApiSuccessResponse -> {

                                value = DataState.data(
                                    AuthViewState(
                                        authToken = AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ),
                                    response = null
                                )

                            }

                            is ApiErrorResponse -> {

                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }

                            is ApiEmptyResponse -> {


                            }
                        }
                    }
                }
            }

    }


}