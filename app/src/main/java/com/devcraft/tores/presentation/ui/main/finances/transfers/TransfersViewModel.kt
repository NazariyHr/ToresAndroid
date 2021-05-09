package com.devcraft.tores.presentation.ui.main.finances.transfers

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.TransfersHistoryData
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class TransfersViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val financesRepository: FinancesRepository
) : BaseViewModel(connectivityLiveData) {

    val data: MutableLiveData<TransfersHistoryData> = MutableLiveData()
    val onGetDataFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun loadData() {
        launchProcess("loadData") {
            val result = financesRepository.getTransfersHistory()
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

    fun refreshData() {
        loadData()
    }
}