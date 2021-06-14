package com.devcraft.tores.utils

import androidx.lifecycle.*

class AllProcessesAreSuccess : LiveData<Boolean>(false) {

    private val allRegisteredProcesses = mutableListOf<String>()
    private val successProcesses = mutableListOf<String>()

    override fun observe(owner: LifecycleOwner, observer: Observer<in Boolean>) {
        super.observe(owner, ObserverWrapper(observer))
    }

    fun registerProcess(processName: String) {
        if (!allRegisteredProcesses.contains(processName)) {
            allRegisteredProcesses.add(processName)
            checkIfAllSuccess()
        }
    }

    fun addSuccessProcess(processName: String) {
        if (!successProcesses.contains(processName)) {
            successProcesses.add(processName)
            checkIfAllSuccess()
        }
    }

    fun removeSuccessProcess(processName: String) {
        if (successProcesses.contains(processName)) {
            successProcesses.remove(processName)
            checkIfAllSuccess()
        }
    }

    private fun checkIfAllSuccess() {
        postValue(successProcesses.containsAll(allRegisteredProcesses))
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
