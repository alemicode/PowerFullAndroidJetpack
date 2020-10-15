package com.example.powerfulljetpack.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.powerfulljetpack.util.DataState

abstract class BaseViewModel<StateEvent, ViewState> : ViewModel() {


    val _stateEvent: MutableLiveData<StateEvent> = MutableLiveData()

    val _viewState: MutableLiveData<ViewState> = MutableLiveData()


    /*
    * this viewState is for actions happended into app like press back ...
    *
     viewState : observerd by :
    * LoginFragment for save and  asigne parameters into edit texts
    *
    * RegistrationFragment for save and  asign parameters into edit texts
    */
    val viewState: LiveData<ViewState>
        get() = _viewState

    /*
    * this viewState is for handling actions about requests or caching of database
    * */

    val dataState: LiveData<DataState<ViewState>> = Transformations
        .switchMap(_stateEvent) {
            it.let {
                handleStateEvent(it)
            }
        }


    fun getCurrentOrNewViewState(): ViewState {
        val value = viewState.value.let {

            it
        } ?: initNewStateEvent()
        return value
    }

    /*
    * it calls dataState live data to call handleStateEvent method to send reqeust
    *
    * or this method called by back btn to call login/register observer to save and cache data into edit text
    * */
    fun setStateEvent(event: StateEvent) {
        _stateEvent.value = event
    }


    abstract fun handleStateEvent(it: StateEvent): LiveData<DataState<ViewState>>

    abstract fun initNewStateEvent(): ViewState


}