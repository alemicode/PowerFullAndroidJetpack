package com.example.powerfulljetpack.ui.main.account

import android.content.Context
import com.example.powerfulljetpack.ui.DataStateChangeListener
import dagger.android.support.DaggerFragment

abstract class BaseAccountFragment : DaggerFragment(){

    val TAG: String = "AppDebug"


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
}
