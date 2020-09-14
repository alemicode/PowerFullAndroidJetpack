package com.example.powerfulljetpack.session

import android.app.Application
import com.example.powerfulljetpack.persistence.AuthTokenDAO
import javax.inject.Inject

class SessionManager @Inject constructor(

    val authTokenDAO: AuthTokenDAO,
    val application: Application

) {

}