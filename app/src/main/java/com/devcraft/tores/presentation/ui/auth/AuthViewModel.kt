package com.devcraft.tores.presentation.ui.auth

import androidx.lifecycle.viewModelScope
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.common_results.ResultStatus
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent
import com.devcraft.tores.utils.SomeProcessAlive
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository,
    connectivityLiveData: ConnectivityInfoLiveData
) : BaseViewModel(connectivityLiveData) {

    val someProcessAlive: SomeProcessAlive = SomeProcessAlive()

    val onLoginSuccess: SingleLiveEvent<String> = SingleLiveEvent()
    val onLoginFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun logIn(login: String, password: String, reCaptchaToken: String) {
        viewModelScope.launch {
            someProcessAlive.addProcess("loginInProcess")

            val result = userRepository.login(login, password, reCaptchaToken)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onLoginSuccess.postValue("Successfully authorised")
                }
                ResultStatus.StateList.FAILURE -> {
                    onLoginFailure.postValue(result.error)
                }
            }

            someProcessAlive.removeProcess("loginInProcess")
        }
    }
}