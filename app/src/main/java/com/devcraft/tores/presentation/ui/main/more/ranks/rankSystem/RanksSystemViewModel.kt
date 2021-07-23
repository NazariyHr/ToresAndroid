package com.devcraft.tores.presentation.ui.main.more.ranks.rankSystem

import androidx.lifecycle.MutableLiveData
import com.devcraft.tores.entities.RankSystemLevel
import com.devcraft.tores.presentation.base.BaseViewModel
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData

class RanksSystemViewModel(
    connectivityLiveData: ConnectivityInfoLiveData
) : BaseViewModel(connectivityLiveData) {

    val rankSystemLevels: MutableLiveData<MutableList<RankSystemLevel>> = MutableLiveData()

    fun loadData() {
        rankSystemLevels.postValue(RankSystemLevel.getAllRankSystemLevels())
    }
}
