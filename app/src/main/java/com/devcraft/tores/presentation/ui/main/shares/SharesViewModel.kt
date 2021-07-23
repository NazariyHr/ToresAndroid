package com.devcraft.tores.presentation.ui.main.shares

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.SharesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.SharesTotalInfo
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class SharesViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val sharesRepository: SharesRepository
) : BaseViewModel(connectivityLiveData) {

    val sharesTotalInfo: MutableLiveData<SharesTotalInfo> = MutableLiveData()
    val onGetSharesTotalInfoFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun loadData() {
        loadSharesTotalInfo()
    }

    fun refreshData() {
        loadSharesTotalInfo()
    }

    private fun loadSharesTotalInfo() {
        val processName = "loadSharesTotalInfo"
        launchProcess(processName) {
            val result = sharesRepository.getSharesTotalInfo()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    sharesTotalInfo.postValue(result.data)
                    removeFailedProcess(processName)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetSharesTotalInfoFailure.postValue(result.status.error)
                    addFailedProcess(processName)
                }
            }
        }
    }
}
