package com.devcraft.tores.presentation.base

import androidx.lifecycle.*
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent
import com.devcraft.tores.utils.AllProcessesAreFailed
import com.devcraft.tores.utils.AllProcessesAreSuccess
import com.devcraft.tores.utils.SomeProcessAlive
import kotlinx.coroutines.launch

open class BaseViewModel(val connectivityLiveData: ConnectivityInfoLiveData) : ViewModel() {

    val isNetworkAvailable: Boolean get() = connectivityLiveData.value ?: false
    val onFailure: SingleLiveEvent<Error> = SingleLiveEvent()
    val someProcessAlive: SomeProcessAlive = SomeProcessAlive()
    val allProcessesAreFailed: AllProcessesAreFailed = AllProcessesAreFailed()
    val allProcessesAreSuccess: AllProcessesAreSuccess = AllProcessesAreSuccess()

    protected fun addFailedProcess(processName: String) {
        allProcessesAreFailed.addFailedProcess(processName)
        allProcessesAreSuccess.removeSuccessProcess(processName)
    }

    protected fun removeFailedProcess(processName: String) {
        allProcessesAreFailed.removeFailedProcess(processName)
        allProcessesAreSuccess.addSuccessProcess(processName)
    }

    protected fun launchProcess(
        processName: String,
        registerProcessForHandlingFailure: Boolean = true,
        body: suspend () -> Unit
    ) {
        viewModelScope.launch {
            someProcessAlive.addProcess(processName)
            if (registerProcessForHandlingFailure) {
                allProcessesAreFailed.registerProcess(processName)
                allProcessesAreSuccess.registerProcess(processName)
            }
            body.invoke()
            someProcessAlive.removeProcess(processName)
        }
    }
}