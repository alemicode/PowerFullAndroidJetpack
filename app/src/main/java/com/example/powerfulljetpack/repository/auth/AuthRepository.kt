package com.example.powerfulljetpack.repository.auth

import com.example.powerfulljetpack.api.auth.OpenApiAuthService
import com.example.powerfulljetpack.persistence.AccountPropertiesDAO
import com.example.powerfulljetpack.persistence.AuthTokenDAO
import com.example.powerfulljetpack.session.SessionManager

class AuthRepository constructor(
    val authTokenDAO: AuthTokenDAO,
    val accountPropertiesDAO: AccountPropertiesDAO,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
) {

}