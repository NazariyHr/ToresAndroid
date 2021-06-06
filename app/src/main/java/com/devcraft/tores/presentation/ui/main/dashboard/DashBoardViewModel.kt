package com.devcraft.tores.presentation.ui.main.dashboard

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.DashboardRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.ProfitsAndRegisters
import com.devcraft.tores.entities.User
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class DashBoardViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val userRepository: UserRepository,
    private val dashboardRepository: DashboardRepository
) : BaseViewModel(connectivityLiveData) {

    val userInfo: MutableLiveData<User> = MutableLiveData()
    val onGetUserFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val profitsAndRegisters: MutableLiveData<ProfitsAndRegisters> = MutableLiveData()
    val onGetProfitsAndRegistersFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun loadData() {
        loadUserInfo()
        loadLastProfitsAndRegisters()
    }

    fun refreshData() {
        loadUserInfo()
        loadLastProfitsAndRegisters()
    }

    private fun loadUserInfo() {
        val processName = "loadUserInfo"
        launchProcess(processName) {
            val result = userRepository.getUser()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    userInfo.postValue(result.data)
                    removeFailedProcess(processName)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetUserFailure.postValue(result.status.error)
                    addFailedProcess(processName)
                }
            }
        }
    }

    private fun loadLastProfitsAndRegisters() {
        val processName = "loadLastProfitsAndRegisters"
        launchProcess(processName) {
            val result = dashboardRepository.getDashboardInfo()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    profitsAndRegisters.postValue(result.data)
                    removeFailedProcess(processName)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetProfitsAndRegistersFailure.postValue(result.status.error)
                    addFailedProcess(processName)
                }
            }
        }
    }
}