package com.devcraft.tores.presentation.ui.main.finances.transfers.transferDetails

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.R
import com.devcraft.tores.app.App
import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.PaymentConfirmationWay
import com.devcraft.tores.entities.TransfersHistoryData
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class TransferDetailsViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val financesRepository: FinancesRepository,
    private val userRepository: UserRepository
) : BaseViewModel(connectivityLiveData) {

    val selectedTransfer: MutableLiveData<TransfersHistoryData.Transaction> =
        MutableLiveData()

    val confirmationWay: MutableLiveData<PaymentConfirmationWay> = MutableLiveData()

    val onTacSubmittedSuccess: SingleLiveEvent<String> = SingleLiveEvent()

    fun refreshData() {
        launchProcess("refreshData") {
            val result = financesRepository.getTransfersHistory()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    result.data?.transactions?.forEach {
                        if (selectedTransfer.value?.id == it.id) {
                            selectedTransfer.postValue(it)
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

    fun submitTac(transactionId: Long, type: TransfersHistoryData.Transaction.Type, tac: String) {
        launchProcess("submitTac") {
            val strType = when (type) {
                TransfersHistoryData.Transaction.Type.TRANSFER_TO_USER -> "transfer"
                TransfersHistoryData.Transaction.Type.TRANSFER_TO_EXCHANGE -> "exchange"
                TransfersHistoryData.Transaction.Type.RECEIVED_FROM_USER -> ""
            }
            val result = financesRepository.submitTac(transactionId, strType, tac)
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onTacSubmittedSuccess.postValue(App.instance.getString(R.string.successfully_submitted))
                }
                ResultStatus.StateList.FAILURE -> {
                    onFailure.postValue(result.error)
                }
            }
        }
    }
}