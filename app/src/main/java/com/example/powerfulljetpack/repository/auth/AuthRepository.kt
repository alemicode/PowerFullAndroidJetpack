package com.example.powerfulljetpack.repository.auth

import com.example.powerfulljetpack.api.auth.OpenApiAuthService
import com.example.powerfulljetpack.persistence.AccountPropertiesDAO
import com.example.powerfulljetpack.persistence.AuthTokenDAO
import com.example.powerfulljetpack.session.SessionManager
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDAO,
    val accountPropertiesDao: AccountPropertiesDAO,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
)
{

}