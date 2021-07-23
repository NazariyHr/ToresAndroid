package com.devcraft.tores.presentation.ui.main.shares.buyShares

import com.devcraft.tores.data.repositories.contract.SharesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class BuySharesViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val sharesRepository: SharesRepository
) : BaseViewModel(connectivityLiveData) {

    val onBuySuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onBuyFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun buy(amount: Int) {
        val processName = "buy"
        launchProcess(processName, false) {
            val result = sharesRepository.buyShares(amount)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onBuySuccess.postValue(true)
                }
                ResultStatus.StateList.FAILURE -> {
                    onBuyFailure.postValue(result.error)
                }
            }
        }
    }
}
