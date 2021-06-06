package com.devcraft.tores.presentation.ui.main.affiliate

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.AffiliateRepository
import com.devcraft.tores.data.repositories.contract.RankRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.AffiliateInfo
import com.devcraft.tores.entities.ProfitsAndRegisters
import com.devcraft.tores.entities.RankInfo
import com.devcraft.tores.entities.User
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class AffiliateViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val affiliateRepository: AffiliateRepository,
    private val rankRepository: RankRepository
) : BaseViewModel(connectivityLiveData) {

    val affiliateInfo: MutableLiveData<AffiliateInfo> = MutableLiveData()
    val onGetAffiliateInfoFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    val rankInfo: MutableLiveData<RankInfo> = MutableLiveData()
    val onGetRankInfoFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun loadData() {
        loadAffiliateInfo()
        loadRankInfo()
    }

    fun refreshData() {
        loadAffiliateInfo()
        loadRankInfo()
    }

    private fun loadAffiliateInfo() {
        val processName = "loadAffiliateInfo"
        launchProcess(processName) {
            val result = affiliateRepository.getAffiliateInfo()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    affiliateInfo.postValue(result.data)
                    removeFailedProcess(processName)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetAffiliateInfoFailure.postValue(result.status.error)
                    addFailedProcess(processName)
                }
            }
        }
    }

    private fun loadRankInfo() {
        val processName = "loadRankInfo"
        launchProcess(processName) {
            val result = rankRepository.getRankInfo()
            when (result.status.status) {
                ResultStatus.StateList.SUCCESS -> {
                    rankInfo.postValue(result.data)
                    removeFailedProcess(processName)
                }
                ResultStatus.StateList.FAILURE -> {
                    onGetRankInfoFailure.postValue(result.status.error)
                    addFailedProcess(processName)
                }
            }
        }
    }
}