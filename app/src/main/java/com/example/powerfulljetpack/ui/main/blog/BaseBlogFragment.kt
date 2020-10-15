package com.example.powerfulljetpack.ui.main.blog

import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerFragment

abstract class BaseBlogFragment : DaggerFragment() {

    val TAG: String = "AppDebug"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancellActiveJobs()
    }

     fun cancellActiveJobs() {
       // viewModel.cancellActiveJob()
    }
}