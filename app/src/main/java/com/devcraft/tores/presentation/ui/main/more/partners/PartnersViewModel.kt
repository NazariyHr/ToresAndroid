package com.devcraft.tores.presentation.ui.main.more.partners

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.PartnersRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.BecomePartnerRequest
import com.devcraft.tores.entities.Partner
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class PartnersViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val partnersRepository: PartnersRepository
) : BaseViewModel(connectivityLiveData) {

    val partners: MutableLiveData<MutableList<Partner>> = MutableLiveData()
    val becomePartnerRequests: MutableLiveData<MutableList<BecomePartnerRequest>> =
        MutableLiveData()
    val dataLoadingFailed: MutableLiveData<Boolean> = MutableLiveData(false)
    val onGetInfoFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun loadData() {
        loadPartnersInfo()
    }

    fun refreshData() {
        loadData()
    }

    private fun loadPartnersInfo() {
        val processName = "loadPartnersInfo"
        launchProcess(processName) {
            val result = partnersRepository.getPartnersAndUserRequests()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    partners.postValue(result.data?.first ?: mutableListOf())
                    becomePartnerRequests.postValue(result.data?.second ?: mutableListOf())
                    dataLoadingFailed.postValue(false)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetInfoFailure.postValue(result.status.error)
                    dataLoadingFailed.postValue(true)
                }
            }
        }
    }
}