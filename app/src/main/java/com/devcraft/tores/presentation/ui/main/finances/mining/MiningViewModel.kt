package com.devcraft.tores.presentation.ui.main.finances.mining

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.MiningHistoryData
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class MiningViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val financesRepository: FinancesRepository
) : BaseViewModel(connectivityLiveData) {

    val data: MutableLiveData<MiningHistoryData> = MutableLiveData()
    val onGetDataFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val onCancelWithdrawSuccess: SingleLiveEvent<String> = SingleLiveEvent()
    val onCancelWithdrawFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    init {
        loadData()
    }

    fun refreshData() {
        loadData()
    }

    private fun loadData() {
        launchProcess("loadData") {
            val result = financesRepository.getMiningHistory()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    data.postValue(result.data)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetDataFailure.postValue(result.status.error)
                }
            }
        }
    }

    fun cancelWithdraw(transactionId: Long) {
        launchProcess("cancelWithdraw") {
            val result = financesRepository.cancelWithdraw(transactionId)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onCancelWithdrawSuccess.postValue("Successfully canceled")
                }
                ResultStatus.StateList.FAILURE -> {
                    onCancelWithdrawFailure.postValue(result.error)
                }
            }
        }
    }
}
