package com.devcraft.tores.presentation.ui.auth

import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class AuthViewModel(
    private val userRepository: UserRepository,
    connectivityLiveData: ConnectivityInfoLiveData
) : BaseViewModel(connectivityLiveData) {

    val onLoginSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onLoginFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun logIn(login: String, password: String, reCaptchaToken: String) {
        val processName = "loginInProcess"
        launchProcess(processName) {
            val result = userRepository.login(login, password, reCaptchaToken)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onLoginSuccess.postValue(true)
                }
                ResultStatus.StateList.FAILURE -> {
                    onLoginFailure.postValue(result.error)
                }
            }
        }
    }
}