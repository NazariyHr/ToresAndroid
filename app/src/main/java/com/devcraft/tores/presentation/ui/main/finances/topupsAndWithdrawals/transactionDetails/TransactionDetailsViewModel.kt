package com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.transactionDetails

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.PaymentConfirmationWay
import com.devcraft.tores.entities.TopupsAndWithdrawalsData
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class TransactionDetailsViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val financesRepository: FinancesRepository,
    private val userRepository: UserRepository
) : BaseViewModel(connectivityLiveData) {

    val selectedTransaction: MutableLiveData<TopupsAndWithdrawalsData.Transaction> =
        MutableLiveData()

    val confirmationWay: MutableLiveData<PaymentConfirmationWay> = MutableLiveData()

    val onCancelTopupSuccess: SingleLiveEvent<String> = SingleLiveEvent()
    val onCancelTopupFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val onTacSubmittedSuccess: SingleLiveEvent<String> = SingleLiveEvent()

    fun refreshData() {
        launchProcess("refreshData") {
            val result = financesRepository.getTopupsAndWithdrawalsData()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    result.data?.transactions?.forEach {
                        if (selectedTransaction.value?.id == it.id) {
                            selectedTransaction.postValue(it)
                            return@forEach
                        }
                    }
                }
                ResultStatus.StateList.FAILURE -> {
                    onFailure.postValue(result.status.error)
                }
            }
        }
    }

    fun cancelTopup(transactionId: Long) {
        launchProcess("cancelTopup") {
            val result = financesRepository.cancelTopup(transactionId)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onCancelTopupSuccess.postValue("Successfully canceled")
                }
                ResultStatus.StateList.FAILURE -> {
                    onCancelTopupFailure.postValue(result.error)
                }
            }
        }
    }

    fun loadUserPaymentConfirmWay() {
        launchProcess("loadUserPaymentConfirmWay") {
            val result = userRepository.getUser()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    confirmationWay.postValue(result.data?.paymentConfirmationWay)
                }
                ResultStatus.StateList.FAILURE -> {
                    onFailure.postValue(result.status.error)
                }
            }
        }
    }

    fun submitTac(transactionId: Long, tac: String) {
        launchProcess("submitTac") {
            val result = financesRepository.submitTac(transactionId, "withdrawal", tac)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onTacSubmittedSuccess.postValue("Successfully submitted")
                }
                ResultStatus.StateList.FAILURE -> {
                    onFailure.postValue(result.error)
                }
            }
        }
    }
}