package com.example.powerfulljetpack.ui.auth

import android.icu.lang.UProperty
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvi.main.util.ApiSuccessResponse
import com.example.mvi.main.util.GenericApiResponse
import com.example.powerfulljetpack.api.auth.network_responses.LoginResponse
import com.example.powerfulljetpack.api.auth.network_responses.RegistrationResponse
import com.example.powerfulljetpack.models.AuthToken
import com.example.powerfulljetpack.repository.auth.AuthRepository
import com.example.powerfulljetpack.ui.BaseViewModel
import com.example.powerfulljetpack.ui.auth.state.AuthStateEvent
import com.example.powerfulljetpack.ui.auth.state.AuthViewState
import com.example.powerfulljetpack.ui.auth.state.LoginFields
import com.example.powerfulljetpack.ui.auth.state.RegistrationField
import com.example.powerfulljetpack.util.AbsentLiveData
import com.example.powerfulljetpack.util.DataState
import javax.inject.Inject

class AuthViewModel @Inject constructor(

    val authRepository: AuthRepository
) : BaseViewModel<AuthStateEvent, AuthViewState>() {


    //
    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {

        when (stateEvent) {
            is AuthStateEvent.LoginAttemptEvent -> {

                return authRepository.login(
                    stateEvent.username,
                    stateEvent.password
                )
            }

            is AuthStateEvent.RegisterAttemptEvent -> {
                return authRepository.registration(
                    stateEvent.email,
                    stateEvent.username,
                    stateEvent.password,
                    stateEvent.confirmPassword
                )

            }

            is AuthStateEvent.CheckPerviuseAuthEvent -> {
                return AbsentLiveData.create()

            }


        }
    }

    /*
        * calling observer of viewState from login and register fragment to save previuse Data
        *
        * calling observer of viewState (if press loign/register btn) to call HandleStateEvent Method and send request to server
        * */
    fun setRegistrationFields(registrationField: RegistrationField) {

        val update = getCurrentOrNewViewState()

        if (update.registrationField == registrationField) {
            return
        }

        update.registrationField = registrationField
        _viewState.value = update

    }


    /*
    * calling observer of viewState from login and register fragment
    * */
    fun setLoginField(loginFields: LoginFields) {
        val update = getCurrentOrNewViewState()
        if (update.LoginFields == loginFields) {
            return
        }
        update.LoginFields = loginFields
        _viewState.value = update
    }


    /*
   * calling observer of viewState from AuthActivity to change state of _cacheToken
   * */
    fun setAuthToken(authToken: AuthToken) {
        val update = getCurrentOrNewViewState()
        if (update.authToken == authToken) {
            return
        }
        update.authToken = authToken

        _viewState.postValue(update)
    }

    override fun initNewStateEvent(): AuthViewState {

        return AuthViewState()
    }


}