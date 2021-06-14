package com.devcraft.tores.presentation.ui.main.dashboard.withdrawTores

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

class WithdrawToresViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val miningRepository: MiningRepository,
    private val financesRepository: FinancesRepository
) : BaseViewModel(connectivityLiveData) {

    val miningInfo: MutableLiveData<MiningInfo> = MutableLiveData()
    val onGetMiningInfoFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val currencyRatesInfo: MutableLiveData<CurrencyRatesInfo> = MutableLiveData()
    val onGetCurrencyRatesFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val onWithdrawSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onWithdrawFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    var selectedCurrencyToWithdraw: Currency? = null

    fun loadData() {
        loadMiningInfo()
        loaCurrencyRatesInfo()
    }

    fun refreshData() {
        loadMiningInfo()
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

    private fun loadMiningInfo() {
        val processName = "loadMiningInfo"
        launchProcess(processName) {
            val result = miningRepository.getMiningInfo()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    miningInfo.postValue(result.data)
                    removeFailedProcess(processName)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetMiningInfoFailure.postValue(result.status.error)
                    addFailedProcess(processName)
                }
            }
        }
    }

    fun withdraw(amount: Double, wallet: String) {
        selectedCurrencyToWithdraw?.let { currency ->
            val processName = "withdraw"
            launchProcess(processName, false) {
                val result = financesRepository.withdraw(amount, currency, wallet)
                when (result.status) {
                    ResultStatus.StateList.SUCCESS -> {
                        onWithdrawSuccess.postValue(true)
                    }
                    ResultStatus.StateList.FAILURE -> {
                        onWithdrawFailure.postValue(result.error)
                    }
                }
            }
        }
    }
}
