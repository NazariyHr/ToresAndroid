package com.devcraft.tores.presentation.ui.main.dashboard.transferTo

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.MiningRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.entities.MiningInfo
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class TransferToViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val miningRepository: MiningRepository,
    private val financesRepository: FinancesRepository
) : BaseViewModel(connectivityLiveData) {

    val miningInfo: MutableLiveData<MiningInfo> = MutableLiveData()
    val onGetMiningInfoFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val onTransferToUserSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onTransferToUserFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val onTransferToExchangeSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onTransferToExchangeFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    var selectedBalanceType: BalanceType? = null

    fun loadData() {
        loadMiningInfo()
    }

    fun refreshData() {
        loadMiningInfo()
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

    fun transferToUser(amount: Double, login: String) {
        selectedBalanceType?.let { balanceType ->
            val processName = "transferToUser"
            launchProcess(processName, false) {
                val result = financesRepository.transferToUser(amount, login, balanceType)
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

    fun transferToExchange(amount: Double, wallet: String) {
        val processName = "transferToExchange"
        launchProcess(processName, false) {
            val result = financesRepository.transferToExchange(amount, wallet)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onTransferToExchangeSuccess.postValue(true)
                }
                ResultStatus.StateList.FAILURE -> {
                    onTransferToExchangeFailure.postValue(result.error)
                }
            }
        }
    }
}
