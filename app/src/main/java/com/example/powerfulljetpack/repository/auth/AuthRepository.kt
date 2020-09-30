package com.example.powerfulljetpack.repository.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.example.mvi.main.util.ApiSuccessResponse
import com.example.mvi.main.util.GenericApiResponse
import com.example.powerfulljetpack.api.auth.OpenApiAuthService
import com.example.powerfulljetpack.api.auth.network_responses.LoginResponse
import com.example.powerfulljetpack.api.auth.network_responses.RegistrationResponse
import com.example.powerfulljetpack.models.AccountProperties
import com.example.powerfulljetpack.models.AuthToken
import com.example.powerfulljetpack.persistence.AccountPropertiesDAO
import com.example.powerfulljetpack.persistence.AuthTokenDAO
import com.example.powerfulljetpack.repository.NetworkBoundResource
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.Response
import com.example.powerfulljetpack.ui.ResponseType
import com.example.powerfulljetpack.ui.auth.state.AuthViewState
import com.example.powerfulljetpack.ui.auth.state.LoginFields
import com.example.powerfulljetpack.ui.auth.state.RegistrationField
import com.example.powerfulljetpack.util.AbsentLiveData
import com.example.powerfulljetpack.util.DataState
import com.example.powerfulljetpack.util.ErrorHandling.Companion.ERROR_SAVE_AUTH_TOKEN
import com.example.powerfulljetpack.util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import com.example.powerfulljetpack.util.PreferenceKeys
import com.example.powerfulljetpack.util.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import java.util.prefs.Preferences
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDAO,
    val accountPropertiesDao: AccountPropertiesDAO,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager,
    val sharedPreferences: SharedPreferences,
    val sharedPreferencesEditor: SharedPreferences.Editor
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


                //no need to save usernane yet
                accountPropertiesDao.insertOrIgnore(
                    AccountProperties(
                        response.body.pk,
                        response.body.email,
                        ""
                    )
                )

                //this will return -1 if falure
                val result = authTokenDao.insert(
                    AuthToken(
                        response.body.pk,
                        response.body.token
                    )
                )

                if (result < 0) {
                    return onCompleteJob(
                        DataState.error(

                            Response(

                                ERROR_SAVE_AUTH_TOKEN, ResponseType.Dialog()
                            )
                        )
                    )
                }

                saveAuthenticationUserToPref(response.body.email)


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


            override suspend fun createCall(): LiveData<GenericApiResponse<LoginResponse>> {

                return openApiAuthService.login(userName, password)
            }

            override fun setJob(job: Job) {

                repositoryJob?.cancel()
                repositoryJob = job

            }


            //not use in this case b/c for login/register we need internet
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


                //no need to save usernane yet
                accountPropertiesDao.insertOrIgnore(
                    AccountProperties(
                        response.body.pk,
                        response.body.email,
                        ""
                    )
                )

                //this will return -1 if falure
                val result = authTokenDao.insert(
                    AuthToken(
                        response.body.pk,
                        response.body.token
                    )
                )

                if (result < 0) {
                    return onCompleteJob(
                        DataState.error(

                            Response(

                                ERROR_SAVE_AUTH_TOKEN, ResponseType.Dialog()
                            )
                        )
                    )
                }

                saveAuthenticationUserToPref(response.body.email)






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


            //not use in this case b/c for login/register we need internet
            override suspend fun createCacheRequestAndReturn() {


            }

        }.asLiveData()

    }


    @InternalCoroutinesApi
    fun checkPreviusellyAuthUser(): LiveData<DataState<AuthViewState>> {

        val previousAuthUserEmail: String? =
            sharedPreferences.getString(PreferenceKeys.PREVIOUS_AUTH_USER, null)

        if (previousAuthUserEmail.isNullOrBlank()) {

            return noTokenFound()
        }
        return object : NetworkBoundResource<Void, AuthViewState>(
            sessionManager.isConnectedToTheInternet(),
            false
        ) {
            override suspend fun createCacheRequestAndReturn() {

                accountPropertiesDao.searchByEmail(previousAuthUserEmail).let { accountProperties ->

                    accountProperties?.let { accountProperties ->
                        if (accountProperties.pk > -1) {

                            authTokenDao.searchByPk(accountProperties.pk).let {

                                if (it != null) {
                                    onCompleteJob(
                                        DataState.data(
                                            data = AuthViewState(authToken = it)
                                        )
                                    )
                                } else {
                                    onCompleteJob(
                                        DataState.data(
                                            response = Response(
                                                RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE,
                                                ResponseType.None()
                                            ),
                                            data = null
                                        )
                                    )
                                }

                            }
                        }
                    }
                }
            }


            //no need to handle
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Void>) {
            }

            override suspend fun createCall(): LiveData<GenericApiResponse<Void>> {

                return AbsentLiveData.create()
            }


            override fun setJob(job: Job) {

                repositoryJob?.cancel()
                repositoryJob = job
            }

        }.asLiveData()

    }

    //NO TOKEN so user should authenticates
    private fun noTokenFound(): LiveData<DataState<AuthViewState>> {

        return object : LiveData<DataState<AuthViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.data(
                    data = null,
                    response = Response(RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE, ResponseType.None())
                )
            }
        }
    }

    //save email into shared prefrenses
    private fun saveAuthenticationUserToPref(email: String) {
        sharedPreferencesEditor.putString(PreferenceKeys.PREVIOUS_AUTH_USER, email).apply()
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
