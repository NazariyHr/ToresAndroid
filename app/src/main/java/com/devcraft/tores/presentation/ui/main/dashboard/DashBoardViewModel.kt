package com.devcraft.tores.presentation.ui.main.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devcraft.tores.data.repositories.contract.DashboardRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.ProfitsAndRegisters
import com.devcraft.tores.entities.User
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent
import kotlinx.coroutines.launch

class DashBoardViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val userRepository: UserRepository,
    private val dashboardRepository: DashboardRepository
) : BaseViewModel(connectivityLiveData) {

    val userInfo: MutableLiveData<User> = MutableLiveData()
    val onGetUserFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val profitsAndRegisters: MutableLiveData<ProfitsAndRegisters> = MutableLiveData()
    val onGetProfitsAndRegistersFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    init {
        loadUserInfo()
        loadLastProfitsAndRegisters()
    }

    private fun loadUserInfo() {
        launchProcess("loadUserInfo") {
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

    private fun loadLastProfitsAndRegisters() {
        launchProcess("loadLastProfitsAndRegisters") {
            val result = dashboardRepository.getDashboardInfo()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    profitsAndRegisters.postValue(result.data)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetProfitsAndRegistersFailure.postValue(result.status.error)
                }
            }
        }
    }
}