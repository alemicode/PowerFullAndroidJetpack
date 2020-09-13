package com.example.powerfulljetpack.ui.auth

import androidx.lifecycle.ViewModel
import com.example.powerfulljetpack.repository.auth.AuthRepository

class AuthViewModel constructor(

    val authRepository: AuthRepository
) : ViewModel() {

}