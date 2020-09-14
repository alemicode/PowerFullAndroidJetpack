package com.example.powerfulljetpack.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.persistence.AppDatabase
import com.example.powerfulljetpack.persistence.AuthTokenDAO
import com.example.powerfulljetpack.ui.BaseActivity
import com.example.powerfulljetpack.viewmodels.ViewModelProviderFactory
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth2)


    }
}
