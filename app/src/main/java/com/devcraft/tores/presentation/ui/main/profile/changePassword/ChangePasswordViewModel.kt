package com.devcraft.tores.presentation.ui.main.profile.changePassword

import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class ChangePasswordViewModel(
    connectivityInfoLiveData: ConnectivityInfoLiveData,
    private val userRepository: UserRepository
) : BaseViewModel(connectivityInfoLiveData) {

    val onChangePasswordSuccess: SingleLiveEvent<String> = SingleLiveEvent()
    val changePasswordFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun changePassword(oldPass: String, newPass: String, newPassConfirm: String) {
        val processName = "changePassword"
        launchProcess(processName) {
            val result = userRepository.changePassword(oldPass, newPass, newPassConfirm)
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onChangePasswordSuccess.postValue(result.data)
                }
                ResultStatus.StateList.FAILURE -> {
                    changePasswordFailure.postValue(result.status.error)
                }
            }
        }
    }
}
