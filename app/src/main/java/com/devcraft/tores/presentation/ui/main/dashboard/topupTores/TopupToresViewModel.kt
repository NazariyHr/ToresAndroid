package com.devcraft.tores.presentation.ui.main.dashboard.topupTores

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.MiningRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.Currency
import com.devcraft.tores.entities.CurrencyRatesInfo
import com.devcraft.tores.entities.MiningInfo
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class TopupToresViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val miningRepository: MiningRepository,
    private val financesRepository: FinancesRepository
) : BaseViewModel(connectivityLiveData) {

    val currencyRatesInfo: MutableLiveData<CurrencyRatesInfo> = MutableLiveData()
    val onGetCurrencyRatesFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val onTopupSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onTopupFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    var selectedCurrencyToTopup: Currency? = null

    fun loadData() {
        loaCurrencyRatesInfo()
    }

    fun refreshData() {
        loaCurrencyRatesInfo()
    }

    private fun loaCurrencyRatesInfo() {
        val processName = "loaCurrencyRatesInfo"
        launchProcess(processName) {
            val result = financesRepository.getCurrencyRates()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    currencyRatesInfo.postValue(result.data)
                    removeFailedProcess(processName)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetCurrencyRatesFailure.postValue(result.status.error)
                    addFailedProcess(processName)
                }
            }
        }
    }

    fun topup(amount: Double) {
        selectedCurrencyToTopup?.let { currency ->
            val processName = "topup"
            launchProcess(processName, false) {
                val result = financesRepository.topup(amount, currency)
                when (result.status) {
                    ResultStatus.StateList.SUCCESS -> {
                        onTopupSuccess.postValue(true)
                    }
                    ResultStatus.StateList.FAILURE -> {
                        onTopupFailure.postValue(result.error)
                    }
                }
            }
        }
    }
}
