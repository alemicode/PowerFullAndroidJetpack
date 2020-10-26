package com.example.powerfulljetpack.ui.main.blog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.powerfulljetpack.ui.DataStateChangeListener
import com.example.powerfulljetpack.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import java.lang.Exception
import javax.inject.Inject

abstract class BaseBlogFragment : DaggerFragment() {

    val TAG: String = "AppDebug"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    lateinit var viewModel: BlogViewModel
    lateinit var dataStateChangeListener: DataStateChangeListener


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(BlogViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        cancellActiveJobs()

        try {
            dataStateChangeListener = activity as DataStateChangeListener
        } catch (e: Exception) {

        }
    }


    fun cancellActiveJobs() {
        // viewModel.cancellActiveJob()
    }
}