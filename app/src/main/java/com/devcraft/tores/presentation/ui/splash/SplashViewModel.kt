package com.devcraft.tores.presentation.ui.splash

import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData

class SplashViewModel(
    connectivityInfoLiveData: ConnectivityInfoLiveData,
    private val tokenRepository: TokenRepository
) : BaseViewModel(connectivityInfoLiveData) {

    fun getToken(): String {
        return tokenRepository.getToken().token
    }
}