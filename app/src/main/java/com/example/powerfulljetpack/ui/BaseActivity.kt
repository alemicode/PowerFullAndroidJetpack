package com.example.powerfulljetpack.ui

import com.example.powerfulljetpack.session.SessionManager
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var sessionManager: SessionManager
    private val debug = "App Debug"





}