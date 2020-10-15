package com.example.powerfulljetpack.di.auth

import android.content.SharedPreferences
import com.example.powerfulljetpack.api.auth.OpenApiAuthService
import com.example.powerfulljetpack.persistence.AccountPropertiesDAO
import com.example.powerfulljetpack.persistence.AuthTokenDAO
import com.example.powerfulljetpack.repository.auth.AuthRepository
import com.example.powerfulljetpack.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): OpenApiAuthService {
        return retrofitBuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }


    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDAO,
        accountPropertiesDao: AccountPropertiesDAO,
        openApiAuthService: OpenApiAuthService,
        sharedPreferences: SharedPreferences,
        sharedpreferencesEditor: SharedPreferences.Editor
    ): AuthRepository {
        return AuthRepository(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager,
            sharedPreferences,
            sharedpreferencesEditor
        )
    }

}