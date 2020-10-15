package com.example.powerfulljetpack.di.main

import androidx.lifecycle.ViewModel
import com.example.powerfulljetpack.di.ViewModelKey
import com.example.powerfulljetpack.ui.main.account.AccountViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel
}