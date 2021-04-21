package com.devcraft.tores.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class SomeProcessAlive : LiveData<Boolean>(false) {

    private val runningProcesses = mutableListOf<String>()

    override fun observe(owner: LifecycleOwner, observer: Observer<in Boolean>) {
        super.observe(owner, ObserverWrapper(observer))
    }

    fun addProcess(processName: String) {
        runningProcesses.add(processName)
        if (runningProcesses.size == 1) {
            postValue(true)
        }
    }

    fun removeProcess(processName: String) {
        runningProcesses.remove(processName)
        if (runningProcesses.size == 0) {
            postValue(false)
        }
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
