package com.example.powerfulljetpack.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.ui.BaseActivity
import com.example.powerfulljetpack.ui.auth.AuthActivity
import com.example.powerfulljetpack.util.Constans
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObserver()

        tool_bar.setOnClickListener {

            sessionManager.logout()
        }
    }


    fun subscribeObserver() {
        sessionManager.cachedToken.observe(this, Observer { authToken ->

            if (authToken == null || authToken.account_pk == -1 || authToken.token == null) {

                navAuthActivity()
            }
        })
    }

    private fun navAuthActivity() {

        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    override fun displayProgressBar(bool: Boolean) {

        if (bool) {

            progress_bar.visibility = View.VISIBLE
        } else {

            progress_bar.visibility = View.GONE
        }
    }

}
