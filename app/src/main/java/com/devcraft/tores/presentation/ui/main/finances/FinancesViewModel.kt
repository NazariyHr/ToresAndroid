package com.devcraft.tores.presentation.ui.main.finances

import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.common.SingleLiveEvent

class FinancesViewModel(
    connectivityLiveData: ConnectivityInfoLiveData
) : BaseViewModel(connectivityLiveData) {
    val needRefreshTopupsAndWithdrawals: SingleLiveEvent<Boolean> = SingleLiveEvent()
}