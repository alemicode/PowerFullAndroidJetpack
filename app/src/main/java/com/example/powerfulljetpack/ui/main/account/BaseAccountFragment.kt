package com.example.powerfulljetpack.ui.main.account

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.ui.DataStateChangeListener
import com.example.powerfulljetpack.ui.main.MainActivity
import com.example.powerfulljetpack.util.LastApiReponseResult
import com.example.powerfulljetpack.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import java.lang.Exception
import javax.inject.Inject

abstract class BaseAccountFragment : DaggerFragment() {

    val TAG: String = "AppDebug"

    lateinit var dataStateChangeListener: DataStateChangeListener


    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AccountViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)




        try {

            dataStateChangeListener = activity as DataStateChangeListener
        } catch (e: Exception) {
            println("${e.printStackTrace()}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(AccountViewModel::class.java)
        } ?: throw Exception("debug : invalid activity")
        cancellActiveJobs()


    }


     fun cancellActiveJobs() {
        viewModel.cancellActiveJob()
    }

}

