package com.example.powerfulljetpack.ui.main.account.state

sealed class AccountStateEvent {


    class GetAccountPropertiesEvent() : AccountStateEvent()

    data class UpdateAcountPropertiesEvent(
        val email: String,
        val userName: String
    ) : AccountStateEvent()

    data class ChangePasswordEvent(
        val currentPassword: String,
        val newPassword: String,
        val confirmPassword: String
    ) : AccountStateEvent()

    class None : AccountStateEvent()

}