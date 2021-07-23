package com.devcraft.tores.presentation.ui.main.shares.sharesHistory

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.SharesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.ShareTransfer
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class SharesHistoryViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val sharesRepository: SharesRepository
) : BaseViewModel(connectivityLiveData) {

    val history: MutableLiveData<MutableList<ShareTransfer>> = MutableLiveData()
    val onGetHistoryFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun loadData() {
        launchProcess("loadData") {
            val result = sharesRepository.getSharesHistory()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    history.postValue(result.data ?: mutableListOf())
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetHistoryFailure.postValue(result.status.error)
                }
            }
        }
    }

    fun refreshData() {
        loadData()
    }
}
