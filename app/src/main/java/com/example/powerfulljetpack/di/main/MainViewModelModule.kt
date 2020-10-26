package com.example.powerfulljetpack.di.main

import androidx.lifecycle.ViewModel
import com.example.powerfulljetpack.di.ViewModelKey
import com.example.powerfulljetpack.repository.main.BlogRepository
import com.example.powerfulljetpack.ui.main.account.AccountViewModel
import com.example.powerfulljetpack.ui.main.blog.BlogViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BlogViewModel::class)
    abstract fun bindBlogViewModel(blogViewModel: BlogViewModel): ViewModel

}