package com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.TopupsAndWithdrawalsData
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class TopupsAndWithdrawalsViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val financesRepository: FinancesRepository
) : BaseViewModel(connectivityLiveData) {

    val data: MutableLiveData<TopupsAndWithdrawalsData> = MutableLiveData()
    val onGetDataFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    init {
        loadData()
    }

    fun refreshData(){
        loadData()
    }

    private fun loadData() {
        launchProcess("loadData") {
            val result = financesRepository.getTopupsAndWithdrawalsData()
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
}