package com.devcraft.tores.presentation.ui.main.profile

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.User
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class ProfileViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) : BaseViewModel(connectivityLiveData) {

    val userInfo: MutableLiveData<User> = MutableLiveData()
    val onGetUserFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val onAuthTokenCleared: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun loadData() {
        loadUserInfo()
    }

    fun refreshData() {
        loadUserInfo()
    }

    fun clearAuthToken() {
        tokenRepository.deleteToken()
        onAuthTokenCleared.postValue(true)
    }

    private fun loadUserInfo() {
        val processName = "loadUserInfo"
        launchProcess(processName) {
            val result = userRepository.getUser()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    userInfo.postValue(result.data)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetUserFailure.postValue(result.status.error)
                }
            }
        }
    }
}
