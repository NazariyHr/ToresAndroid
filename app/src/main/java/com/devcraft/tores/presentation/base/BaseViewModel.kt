package com.devcraft.tores.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent
import com.devcraft.tores.utils.SomeProcessAlive
import kotlinx.coroutines.launch

open class BaseViewModel(val connectivityLiveData: ConnectivityInfoLiveData) : ViewModel() {

    val isNetworkAvailable: Boolean get() = connectivityLiveData.value ?: false

    val someProcessAlive: SomeProcessAlive = SomeProcessAlive()

    val onFailure: SingleLiveEvent<Error> = SingleLiveEvent()

    fun launchProcess(processName: String, body: suspend () -> Unit) {
        viewModelScope.launch {
            someProcessAlive.addProcess(processName)
            body.invoke()
            someProcessAlive.removeProcess(processName)
        }
    }
}