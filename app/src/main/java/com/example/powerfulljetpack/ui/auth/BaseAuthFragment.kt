package com.example.powerfulljetpack.ui.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.powerfulljetpack.viewmodels.ViewModelProviderFactory
import dagger.android.AndroidInjection
import dagger.android.support.DaggerFragment
import java.lang.Exception
import javax.inject.Inject

abstract class BaseAuthFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: AuthViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(viewModel::class.java)
        } ?: throw Exception("activity is not ailve")

        cancellActiveJob()
    }

    private fun cancellActiveJob() {
        viewModel.cancellActiveJob()
    }
}