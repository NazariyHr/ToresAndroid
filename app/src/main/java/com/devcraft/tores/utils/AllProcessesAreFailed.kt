package com.devcraft.tores.utils

import androidx.lifecycle.*

class AllProcessesAreFailed : LiveData<Boolean>(false) {

    private val allRegisteredProcesses = mutableListOf<String>()
    private val failedProcesses = mutableListOf<String>()

    override fun observe(owner: LifecycleOwner, observer: Observer<in Boolean>) {
        super.observe(owner, ObserverWrapper(observer))
    }

    fun registerProcess(processName: String) {
        if (!allRegisteredProcesses.contains(processName)) {
            allRegisteredProcesses.add(processName)
            checkIfAllFailed()
        }
    }

    fun addFailedProcess(processName: String) {
        if (!failedProcesses.contains(processName)) {
            failedProcesses.add(processName)
            checkIfAllFailed()
        }
    }

    fun removeFailedProcess(processName: String) {
        if (failedProcesses.contains(processName)) {
            failedProcesses.remove(processName)
            checkIfAllFailed()
        }
    }

    private fun checkIfAllFailed() {
        postValue(failedProcesses.containsAll(allRegisteredProcesses))
    }

    private class ObserverWrapper(private val observer: Observer<in Boolean>) : Observer<Boolean> {
        private var lastValue: Boolean? = null

        override fun onChanged(t: Boolean?) {
            if (lastValue != t) {
                lastValue = t
                observer.onChanged(t)
            }
        }
    }
}
