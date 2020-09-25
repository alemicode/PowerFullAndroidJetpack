package com.example.powerfulljetpack.di


import com.example.powerfulljetpack.di.auth.AuthFragmentBuildersModule
import com.example.powerfulljetpack.di.auth.AuthModule
import com.example.powerfulljetpack.di.auth.AuthScope
import com.example.powerfulljetpack.di.auth.AuthViewModelModule
import com.example.powerfulljetpack.ui.auth.AuthActivity
import com.example.powerfulljetpack.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity


    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

}