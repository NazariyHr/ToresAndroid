package com.devcraft.tores.presentation.base

import androidx.lifecycle.ViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData

open class BaseViewModel(val connectivityLiveData: ConnectivityInfoLiveData) : ViewModel() {

    val isNetworkAvailable: Boolean get() = connectivityLiveData.value ?: false
}