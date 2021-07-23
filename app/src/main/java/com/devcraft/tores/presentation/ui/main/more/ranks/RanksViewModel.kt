package com.devcraft.tores.presentation.ui.main.more.ranks

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.RankRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.RankInfo
import com.devcraft.tores.entities.UserRankSystemInfo
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class RanksViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val rankRepository: RankRepository
) : BaseViewModel(connectivityLiveData) {

    val userRankSystemInfo: MutableLiveData<UserRankSystemInfo> = MutableLiveData()
    val onGetUserRankSystemInfoFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val userRankInfo: MutableLiveData<RankInfo> = MutableLiveData()
    val onGeUserRankInfoFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun loadData() {
        loadUserRankSystemInfo()
        loadRankInfo()
    }

    fun refreshData() {
        loadData()
    }

    private fun loadUserRankSystemInfo() {
        val processName = "loadUserRankSystemInfo"
        launchProcess(processName) {
            val result = rankRepository.getUserRankSystemInfo()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    userRankSystemInfo.postValue(result.data)
                    removeFailedProcess(processName)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetUserRankSystemInfoFailure.postValue(result.status.error)
                    addFailedProcess(processName)
                }
            }
        }
    }

    private fun loadRankInfo() {
        val processName = "loadRankInfo"
        launchProcess(processName) {
            val result = rankRepository.getRankInfo()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    userRankInfo.postValue(result.data)
                    removeFailedProcess(processName)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGeUserRankInfoFailure.postValue(result.status.error)
                    addFailedProcess(processName)
                }
            }
        }
    }
}
