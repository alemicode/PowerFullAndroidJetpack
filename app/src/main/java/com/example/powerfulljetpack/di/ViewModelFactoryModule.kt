package com.example.powerfulljetpack.di

import androidx.lifecycle.ViewModelProvider
import com.example.powerfulljetpack.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}