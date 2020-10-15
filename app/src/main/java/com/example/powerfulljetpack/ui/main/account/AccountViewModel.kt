package com.example.powerfulljetpack.ui.main.account

import androidx.lifecycle.LiveData
import com.example.powerfulljetpack.models.AccountProperties
import com.example.powerfulljetpack.repository.main.AccountRepository
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.BaseViewModel
import com.example.powerfulljetpack.ui.auth.state.AuthStateEvent
import com.example.powerfulljetpack.ui.main.account.state.AccountStateEvent
import com.example.powerfulljetpack.ui.main.account.state.AccountViewState
import com.example.powerfulljetpack.util.AbsentLiveData
import com.example.powerfulljetpack.util.DataState
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class AccountViewModel @Inject constructor(

    val sessionManager: SessionManager,
    val accountRepository: AccountRepository
) : BaseViewModel<AccountStateEvent, AccountViewState>() {


    @InternalCoroutinesApi
    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {

        when (stateEvent) {
            is AccountStateEvent.GetAccountPropertiesEvent -> {


                return sessionManager.cachedToken.value?.let {
                    it?.let {
                        accountRepository.getAccountProperties(it)

                    }
                } ?: AbsentLiveData.create()

            }

            is AccountStateEvent.UpdateAcountPropertiesEvent -> {
                sessionManager.cachedToken.value?.let { authToken ->
                    authToken.account_pk?.let {
                        return accountRepository.saveAccountProperties(
                            authToken,
                            AccountProperties(
                                it,
                                stateEvent.email,
                                stateEvent.userName
                            )

                        )
                    }


                }

            }

            is AccountStateEvent.ChangePasswordEvent -> {

                sessionManager.cachedToken?.value?.let { authToken ->

                    authToken.token?.let {

                        return accountRepository.updatePassword(
                            authToken,
                            stateEvent.currentPassword,
                            stateEvent.newPassword,
                            stateEvent.confirmPassword
                        )
                    }
                }

            }

            is AccountStateEvent.None -> {

                return object : LiveData<DataState<AccountViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        value = DataState.data(null,null)
                    }
                }
            }

        }

        return AbsentLiveData.create()

    }


    override fun initNewStateEvent(): AccountViewState {

        return AccountViewState()
    }

    fun setAccountPropertiesData(accountViewState: AccountViewState) {
        val update = getCurrentOrNewViewState()
        if (update.accountProperties == accountViewState.accountProperties) {
            return
        } else {
            println("setvalue : ${update.accountProperties}")
            _viewState.value = accountViewState
        }
    }

    fun handlePendingData() {
        setStateEvent(AccountStateEvent.None())
    }


    /*
    * this methid cancell the current job working on auth Repository
    * */
    fun cancellActiveJob() {

        handlePendingData()
        accountRepository.cancellActiveJobs()

    }

    override fun onCleared() {
        super.onCleared()
        cancellActiveJob()
    }

    fun logOut() {
        sessionManager.logout()
    }
}

