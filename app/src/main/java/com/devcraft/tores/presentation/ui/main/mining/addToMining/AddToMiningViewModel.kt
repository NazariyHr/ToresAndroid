package com.devcraft.tores.presentation.ui.main.mining.addToMining

import com.devcraft.tores.data.repositories.contract.MiningRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class AddToMiningViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val miningRepository: MiningRepository
) : BaseViewModel(connectivityLiveData) {

    val onAddToMiningSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onAddToMiningFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    var selectedBalanceType: BalanceType? = null

    fun addToMining(amount: Double) {
        selectedBalanceType?.let {
            val processName = "addToMining"
            launchProcess(processName) {
                val result = miningRepository.addToMining(amount, it)
                when (result.status) {
                    ResultStatus.StateList.SUCCESS -> {
                        onAddToMiningSuccess.postValue(true)
                    }
                    ResultStatus.StateList.FAILURE -> {
                        onAddToMiningFailure.postValue(result.error)
                    }
                }
            }
        }
    }
}
