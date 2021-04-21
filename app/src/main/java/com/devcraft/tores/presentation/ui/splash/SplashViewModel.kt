package com.devcraft.tores.presentation.ui.splash

import androidx.lifecycle.ViewModel
import com.devcraft.tores.data.repositories.contract.TokenRepository

class SplashViewModel(private val tokenRepository: TokenRepository) : ViewModel() {

    fun getToken(): String {
        return tokenRepository.getToken().token
    }
}