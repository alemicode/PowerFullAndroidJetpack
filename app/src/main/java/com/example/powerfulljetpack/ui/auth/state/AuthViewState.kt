package com.example.powerfulljetpack.ui.auth.state

import com.example.powerfulljetpack.models.AuthToken


/*
* most of important things are behind this class
* AuthToken :
* when you logIn or Register there is an observer for viewState which check if authToken class changed
* login method in sessionManager should be called to change state of cachedToken LiveData
*
* LoginFields:
* if you press back liveData for viewStated is called and they call observer of it from loginFragment and RegisterFragment
*
* */

data class AuthViewState(

    var registrationField: RegistrationField = RegistrationField(),
    var LoginFields: LoginFields? = LoginFields(),
    var authToken: AuthToken? = AuthToken()
)


data class RegistrationField(

    var registration_email: String? = null,
    var registration_username: String? = null,
    var registration_password: String? = null,
    var registration_confirm_password: String? = null
) {

    class RegistraetionError() {

        companion object {

            fun mostFillAllFields(): String {

                return "All Fields Are Required"
            }

            fun passwordDoNotMatch(): String {

                return "Passwords Are Not Match"

            }

            fun none(): String {

                return "None"
            }

        }

    }

    fun isValidForRegistration(): String {

        if (registration_email.isNullOrEmpty() ||
            registration_username.isNullOrEmpty() ||
            registration_password.isNullOrEmpty() ||
            registration_confirm_password.isNullOrEmpty()
        ) {
            return RegistraetionError.mostFillAllFields()
        } else if (!(registration_password.equals(registration_confirm_password))) {
            return RegistraetionError.passwordDoNotMatch()
        } else {
            return RegistraetionError.none()
        }


    }


}


data class LoginFields(

    var login_email: String? = null,
    var login_password: String? = null

) {

    class LoginError {
        companion object {
            fun mostFillAllData(): String {

                return "All Fields Are Required"
            }

            fun none(): String {
                return "None"

            }
        }
    }


    fun isValidForLogin(): String {

        if (login_email.isNullOrEmpty() ||
            login_password.isNullOrEmpty()
        ) {
            return LoginError.mostFillAllData()
        } else {
            return LoginError.none()
        }
    }

}







