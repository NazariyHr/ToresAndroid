package com.devcraft.tores.presentation.ui.main.more.partners.becomePartner

import com.devcraft.tores.data.repositories.contract.PartnersRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class BecomePartnerViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val partnersRepository: PartnersRepository
) : BaseViewModel(connectivityLiveData) {

    val onBecomePartnerSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val onBecomePartnerFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun sendBecomePartnerRequest(
        companyName: String,
        url: String,
        contacts: String,
        percent: String?,
        type: String?,
        additional: String?
    ) {
        val processName = "sendBecomePartnerRequest"
        launchProcess(processName) {
            val result = partnersRepository.becomePartner(
                companyName,
                url,
                contacts,
                percent,
                type,
                additional
            )
            when (result.status) {
                ResultStatus.StateList.SUCCESS -> {
                    onBecomePartnerSuccess.postValue(true)
                }
                ResultStatus.StateList.FAILURE -> {
                    onBecomePartnerFailure.postValue(result.error)
                }
            }
        }
    }
}