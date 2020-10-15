package com.example.powerfulljetpack.di.main

import com.example.powerfulljetpack.api.main.OpenApiMainService
import com.example.powerfulljetpack.models.AccountProperties
import com.example.powerfulljetpack.persistence.AccountPropertiesDAO
import com.example.powerfulljetpack.repository.main.AccountRepository
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.main.account.AccountFragment
import com.example.powerfulljetpack.ui.main.account.ChangePasswordFragment
import com.example.powerfulljetpack.ui.main.account.UpdateAccountFragment
import com.example.powerfulljetpack.ui.main.blog.BlogFragment
import com.example.powerfulljetpack.ui.main.blog.UpdateBlogFragment
import com.example.powerfulljetpack.ui.main.blog.ViewBlogFragment
import com.example.powerfulljetpack.ui.main.create_blog.CreateBlogFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideOpenApiMainService(retrofitBuilder: Retrofit.Builder): OpenApiMainService {
        return retrofitBuilder
            .build()
            .create(OpenApiMainService::class.java)
    }

    @MainScope
    @Provides
    fun provideAccountRepository(
        sessionManager: SessionManager,
        accountPropertiesDAO: AccountPropertiesDAO,
        openApiMainService: OpenApiMainService

    ): AccountRepository {
        return AccountRepository(openApiMainService, accountPropertiesDAO, sessionManager)
    }
}