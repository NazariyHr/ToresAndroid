package com.devcraft.tores.presentation.ui.main.shares.transferShares

import com.devcraft.tores.data.repositories.contract.SharesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class TransferSharesViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val sharesRepository: SharesRepository
) : BaseViewModel(connectivityLiveData) {

    val onTransferToUserSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onTransferToUserFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun transferShares(amount: Int, login: String) {
        val processName = "transferShares"
        launchProcess(processName, false) {
            val result = sharesRepository.transferSharesToUser(amount, login)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onTransferToUserSuccess.postValue(true)
                }
                ResultStatus.StateList.FAILURE -> {
                    onTransferToUserFailure.postValue(result.error)
                }
            }
        }
    }
}
