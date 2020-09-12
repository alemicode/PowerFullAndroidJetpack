package com.example.powerfulljetpack.session

import android.app.Application
import com.example.powerfulljetpack.persistence.AuthTokenDAO

class SessionManager constructor(

    val authTokenDAO: AuthTokenDAO,
    val application: Application

) {

}