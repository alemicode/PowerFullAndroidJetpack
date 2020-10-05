package com.example.powerfulljetpack.ui.main.account

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.ui.DataStateChangeListener
import dagger.android.support.DaggerFragment

abstract class BaseAccountFragment : DaggerFragment(){

    val TAG: String = "AppDebug"


    override fun onAttach(context: Context) {
        super.onAttach(context)


    }
}
