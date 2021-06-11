package com.devcraft.tores.presentation.ui.main.profile.financePassword

import com.devcraft.tores.R
import com.devcraft.tores.app.App
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class FinancePasswordViewModel(
    connectivityInfoLiveData: ConnectivityInfoLiveData,
    private val userRepository: UserRepository
) : BaseViewModel(connectivityInfoLiveData) {

    val setFinancePasswordSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onSetFinancePasswordFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val removeFinancePasswordSuccess: SingleLiveEvent<String> = SingleLiveEvent()
    val onRemoveFinancePasswordFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun setFinancePassword(pass: String, passConfirm: String) {
        val processName = "setFinancePassword"
        launchProcess(processName) {
            val result = userRepository.setFinancePassword(pass, passConfirm)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    setFinancePasswordSuccess.postValue(true)
                }
                ResultStatus.StateList.FAILURE -> {
                    onSetFinancePasswordFailure.postValue(result.error)
                }
            }
        }
    }

    fun removeFinancePassword() {
        val processName = "removeFinancePassword"
        launchProcess(processName) {
            val result = userRepository.removeFinancePassword()
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    val successStr = App.instance.getString(R.string.finance_password_reset_success)
                    removeFinancePasswordSuccess.postValue(successStr)
                }
                ResultStatus.StateList.FAILURE -> {
                    val limitStr = App.instance.getString(R.string.finance_password_reset_limit)
                    val errStr = result.error?.message.orEmpty()
                    onRemoveFinancePasswordFailure.postValue(
                        if (errStr == "limit") Error(limitStr) else result.error
                    )
                }
            }
        }
    }
}