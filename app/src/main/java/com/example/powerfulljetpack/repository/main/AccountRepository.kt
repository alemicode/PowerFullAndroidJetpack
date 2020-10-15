package com.example.powerfulljetpack.repository.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.mvi.main.util.ApiSuccessResponse
import com.example.mvi.main.util.GenericApiResponse
import com.example.powerfulljetpack.api.GenericResponse
import com.example.powerfulljetpack.api.main.OpenApiMainService
import com.example.powerfulljetpack.models.AccountProperties
import com.example.powerfulljetpack.models.AuthToken
import com.example.powerfulljetpack.persistence.AccountPropertiesDAO
import com.example.powerfulljetpack.repository.JobManager
import com.example.powerfulljetpack.repository.NetworkBoundResource
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.Response
import com.example.powerfulljetpack.ui.ResponseType
import com.example.powerfulljetpack.ui.main.account.state.AccountViewState
import com.example.powerfulljetpack.util.AbsentLiveData
import com.example.powerfulljetpack.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject


class AccountRepository
@Inject
constructor(
    val openApiMainService: OpenApiMainService,
    val accountPropertiesDao: AccountPropertiesDAO,
    val sessionManager: SessionManager
) : JobManager("AccountRepository") {

    private val TAG: String = "AppDebug"

    private var repositoryJob: Job? = null


    @InternalCoroutinesApi
    fun getAccountProperties(authToken: AuthToken): LiveData<DataState<AccountViewState>> {
        return object :
            NetworkBoundResource<AccountProperties, AccountProperties, AccountViewState>(
                sessionManager.isConnectedToTheInternet(),
                true,
                true,
                false
            ) {

            // if network is down, view the cache and return
            override suspend fun createCacheRequestAndReturn() {
                withContext(Dispatchers.Main) {

                    // finishing by viewing db cache
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(viewState, null))
                    }
                }
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<AccountProperties>) {
                updateLocalDb(response.body)

                createCacheRequestAndReturn()
            }

            override fun loadFromCache(): LiveData<AccountViewState> {

                return accountPropertiesDao.searchByPk(authToken.account_pk!!)
                    ?.switchMap {
                        object : LiveData<AccountViewState>() {
                            override fun onActive() {
                                super.onActive()
                                value = AccountViewState(it)
                            }
                        }
                    }!!
            }

            override suspend fun updateLocalDb(cacheObject: AccountProperties?) {
                cacheObject?.let {
                    accountPropertiesDao.updateAccountProperties(
                        cacheObject.email,
                        cacheObject.userName,
                        cacheObject.pk
                    )
                }
            }

            override fun createCall(): LiveData<GenericApiResponse<AccountProperties>> {
                return openApiMainService
                    .getAccountProperties(
                        "Token ${authToken.token!!}"
                    )
            }


            override fun setJob(job: Job) {
                addjob("getAccountProperties", job)
            }


        }.asLiveData()
    }

    @InternalCoroutinesApi
    fun saveAccountProperties(
        authToken: AuthToken,
        accountProperties: AccountProperties
    ): LiveData<DataState<AccountViewState>> {

        return object : NetworkBoundResource<GenericResponse, Any, AccountViewState>(
            sessionManager.isConnectedToTheInternet(),
            true,
            false,
            false
        ) {
            //ignore
            override suspend fun createCacheRequestAndReturn() {
                TODO("Not yet implemented")
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<GenericResponse>) {

                updateLocalDb(null)
                withContext(Main) {
                    onCompleteJob(
                        DataState.data(
                            data = null,
                            response = Response(
                                message = response.body.response,
                                responseType = ResponseType.Toast()
                            )
                        )
                    )
                }
            }

            override fun createCall(): LiveData<GenericApiResponse<GenericResponse>> {

                return openApiMainService.saveAccountProperties(
                    "Token ${authToken.token}",
                    accountProperties.email,
                    accountProperties.userName

                )
            }

            //ignore
            override fun loadFromCache(): LiveData<AccountViewState> {


                return AbsentLiveData.create()
            }

            override suspend fun updateLocalDb(cacheObject: Any?) {

                return accountPropertiesDao.updateAccountProperties(
                    accountProperties.email,
                    accountProperties.userName,
                    accountProperties.pk
                )
            }

            override fun setJob(job: Job) {
                addjob("saveAccountProperties", job)
            }

        }.asLiveData()
    }


    @InternalCoroutinesApi
    fun updatePassword(
        authToken: AuthToken,
        currentPassword: String,
        new_password: String,
        confirm_new_password: String
    ): LiveData<DataState<AccountViewState>> {
        return object : NetworkBoundResource<GenericResponse, Any, AccountViewState>(
            sessionManager.isConnectedToTheInternet(),
            true,
            false,
            true
        ) {
            override suspend fun createCacheRequestAndReturn() {
                TODO("Not yet implemented")
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<GenericResponse>) {

                updateLocalDb(null)

                withContext(Main) {
                    onCompleteJob(
                        DataState.data(
                            null,
                            response = Response(response.body.response, ResponseType.Toast())
                        )
                    )
                }

            }

            override fun createCall(): LiveData<GenericApiResponse<GenericResponse>> {
                return openApiMainService.updatePassword(
                    "Token ${authToken.token}",
                    currentPassword,
                    new_password,
                    confirm_new_password
                )
            }

            override fun loadFromCache(): LiveData<AccountViewState> {
                TODO("Not yet implemented")
            }

            override suspend fun updateLocalDb(cacheObject: Any?) {

            }

            override fun setJob(job: Job) {

                addjob("updatePassword", job)
            }

        }.asLiveData()
    }



}



