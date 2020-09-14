package com.example.powerfulljetpack.ui.auth

import androidx.lifecycle.ViewModel
import com.example.powerfulljetpack.repository.auth.AuthRepository
import javax.inject.Inject

class AuthViewModel @Inject constructor(

    val authRepository: AuthRepository
) : ViewModel() {

}