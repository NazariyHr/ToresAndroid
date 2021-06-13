package com.devcraft.tores.presentation.ui.main.mining

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.MiningRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.MiningInfo
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class MiningViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val miningRepository: MiningRepository
) : BaseViewModel(connectivityLiveData) {

    val miningInfo: MutableLiveData<MiningInfo> = MutableLiveData()
    val onGetMiningInfoFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun loadData() {
        loadMiningInfo()
    }

    fun refreshData() {
        loadMiningInfo()
    }

    private fun loadMiningInfo() {
        val processName = "loadMiningInfo"
        launchProcess(processName) {
            val result = miningRepository.getMiningInfo()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    miningInfo.postValue(result.data)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetMiningInfoFailure.postValue(result.status.error)
                }
            }
        }
    }
}
