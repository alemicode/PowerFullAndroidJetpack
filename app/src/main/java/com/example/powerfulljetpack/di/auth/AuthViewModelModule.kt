package com.example.powerfulljetpack.di.auth

import androidx.lifecycle.ViewModel

import com.example.powerfulljetpack.di.ViewModelKey
import com.example.powerfulljetpack.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}