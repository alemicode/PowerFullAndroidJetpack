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
import com.example.powerfulljetpack.repository.NetworkBoundResource
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.Data
import com.example.powerfulljetpack.ui.Response
import com.example.powerfulljetpack.ui.ResponseType
import com.example.powerfulljetpack.ui.auth.state.AuthViewState
import com.example.powerfulljetpack.ui.auth.state.LoginFields
import com.example.powerfulljetpack.ui.auth.state.RegistrationField
import com.example.powerfulljetpack.util.DataState
import com.example.powerfulljetpack.util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDAO,
    val accountPropertiesDao: AccountPropertiesDAO,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
) {

    private var repositoryJob: Job? = null

    @InternalCoroutinesApi
    fun attemptLogin(userName: String, password: String): LiveData<DataState<AuthViewState>> {


        var loginError = LoginFields(userName, password).isValidForLogin()
        if (!loginError.equals(LoginFields.LoginError.none())) {


            return returnOnErrorReponse(loginError, ResponseType.Dialog())
        }

        return object : NetworkBoundResource<LoginResponse, AuthViewState>(
            // sessionManager.isConnectedToTheInternet()
            true,
            true
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<LoginResponse>) {

                if (response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    return onErrorReturn(response.body.errorMessage, false, true)
                }


                onCompleteJob(
                    DataState.data(
                        AuthViewState(
                            authToken = AuthToken(
                                response.body.pk,
                                response.body.token
                            )

                        ),
                        response = Response("f",ResponseType.Dialog())
                    )
                )
            }

            override suspend fun createCall(): LiveData<GenericApiResponse<LoginResponse>> {

                return openApiAuthService.login(userName, password)
            }

            override fun setJob(job: Job) {

                repositoryJob?.cancel()
                repositoryJob = job

            }

            override suspend fun createCacheRequestAndReturn() {
                TODO("Not yet implemented")
            }

        }.asLiveData()
    }


    @InternalCoroutinesApi
    fun attemptRegister(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>>? {
        var regError = RegistrationField(email, username, password, confirmPassword)
            .isValidForRegistration()
        if (!regError.equals("None")) {

            return returnOnErrorReponse(regError, ResponseType.Dialog())
        }
        return object : NetworkBoundResource<RegistrationResponse, AuthViewState>(
            //sessionManager.isConnectedToTheInternet()
            true,
            true
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<RegistrationResponse>) {

                onCompleteJob(


                    DataState.data(
                        AuthViewState(

                            authToken = AuthToken(
                                response.body.pk,
                                response.body.token
                            )
                        ),
                        response = null
                    )
                )
            }

            override suspend fun createCall(): LiveData<GenericApiResponse<RegistrationResponse>> {

                return openApiAuthService.register(email, username, password, confirmPassword)
            }

            override fun setJob(job: Job) {

                repositoryJob?.cancel()
                repositoryJob = job
            }

            override suspend fun createCacheRequestAndReturn() {


            }

        }.asLiveData()

    }

    fun cancellActiveJob() {
        // repositoryJob?.cancel()
    }

    private fun returnOnErrorReponse(

        loginError: String,
        dialog: ResponseType.Dialog
    ): LiveData<DataState<AuthViewState>> {


        println("debug : ${loginError}")
        return object : LiveData<DataState<AuthViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.error(
                    Response(
                        loginError,
                        dialog
                    )
                )

            }
        }
    }


}