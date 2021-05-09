package com.devcraft.tores.presentation.ui.main.finances.rankProfits

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.RankProfitsHistoryData
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class RankProfitsViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val financesRepository: FinancesRepository
) : BaseViewModel(connectivityLiveData) {

    val data: MutableLiveData<RankProfitsHistoryData> = MutableLiveData()
    val onGetDataFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun loadData() {
        launchProcess("loadData") {
            val result = financesRepository.getRankProfitsHistory()
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
