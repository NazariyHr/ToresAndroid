package com.devcraft.tores.presentation.ui.main.more.affiliate.history

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.data.repositories.contract.AffiliateRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.entities.AffiliateTreeUser
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class AffiliateHistoryViewModel(
    connectivityLiveData: ConnectivityInfoLiveData,
    private val affiliateRepository: AffiliateRepository
) : BaseViewModel(connectivityLiveData) {

    val selectedTreeUsersHistory: MutableLiveData<MutableList<AffiliateTreeUser>> =
        MutableLiveData()
    val usersInLine: MutableLiveData<MutableList<AffiliateTreeUser>?> = MutableLiveData()
    val onGetAffiliateTreeUsersFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    private var selectedUser: AffiliateTreeUser? = null

    fun loadData() {
        loadTree()
    }

    fun refreshData() {
        loadTree()
    }

    fun onFirstLineClicked() {
        selectedUser = null
        usersInLine.postValue(null)
        selectedTreeUsersHistory.postValue(mutableListOf())
        loadData()
    }

    fun onUserClickFromHistory(user: AffiliateTreeUser) {
        selectedUser = user
        usersInLine.postValue(null)
        val newHistory = mutableListOf<AffiliateTreeUser>()
        selectedTreeUsersHistory.value?.let {
            run brake@{
                it.forEach {
                    newHistory.add(it)
                    if (it.id == user.id) {
                        return@brake
                    }
                }
            }
        }
        selectedTreeUsersHistory.postValue(newHistory)
        loadData()
    }

    fun onClickUserInLine(user: AffiliateTreeUser) {
        selectedUser = user
        usersInLine.postValue(null)
        val newHistory = mutableListOf<AffiliateTreeUser>()
        selectedTreeUsersHistory.value?.let { newHistory.addAll(it) }
        newHistory.add(user)
        selectedTreeUsersHistory.postValue(newHistory)
        loadData()
    }

    private fun loadTree() {
        if (selectedUser != null) {
            val processName = "getAffiliateTreeSpecificLine"
            launchProcess(processName) {
                val result = affiliateRepository.getAffiliateTreeSpecificLine(
                    selectedUser!!.id,
                    selectedUser!!.line + 1
                )
                when (result.status.status) {
                    ResultStatus.StateList.SUCCESS -> {
                        usersInLine.postValue(result.data)
                        removeFailedProcess(processName)
                    }
                    ResultStatus.StateList.FAILURE -> {
                        onGetAffiliateTreeUsersFailure.postValue(result.status.error)
                        addFailedProcess(processName)
                    }
                }
            }
        } else {
            val processName = "getAffiliateTreeFirstLine"
            launchProcess(processName) {
                val result = affiliateRepository.getAffiliateTreeFirstLine()
                when (result.status.status) {
                    ResultStatus.StateList.SUCCESS -> {
                        usersInLine.postValue(result.data)
                        removeFailedProcess(processName)
                    }
                    ResultStatus.StateList.FAILURE -> {
                        onGetAffiliateTreeUsersFailure.postValue(result.status.error)
                        addFailedProcess(processName)
                    }
                }
            }
        }
    }
}
