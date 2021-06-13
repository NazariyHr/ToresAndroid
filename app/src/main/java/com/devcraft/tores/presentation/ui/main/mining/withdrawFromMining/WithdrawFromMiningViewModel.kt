package com.devcraft.tores.presentation.ui.main.mining.withdrawFromMining

import com.devcraft.tores.data.repositories.contract.MiningRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class WithdrawFromMiningViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val miningRepository: MiningRepository
) : BaseViewModel(connectivityLiveData) {

    val onWithdrawFromMiningSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onWithdrawFromMiningFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun withdrawFromMining(amount: Double) {
        val processName = "addToMining"
        launchProcess(processName) {
            val result = miningRepository.withdrawFromMining(amount)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onWithdrawFromMiningSuccess.postValue(true)
                }
                ResultStatus.StateList.FAILURE -> {
                    onWithdrawFromMiningFailure.postValue(result.error)
                }
            }
        }
    }
}
