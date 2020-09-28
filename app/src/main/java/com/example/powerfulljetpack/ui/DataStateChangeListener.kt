package com.example.powerfulljetpack.ui

import com.example.powerfulljetpack.util.DataState

interface DataStateChangeListener {

    fun onDataStateChange(dataState: DataState<*>?)
}