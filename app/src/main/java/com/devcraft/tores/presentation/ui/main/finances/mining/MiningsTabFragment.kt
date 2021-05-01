package com.devcraft.tores.presentation.ui.main.finances.mining

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MiningsTabFragment : BaseFragment(R.layout.fragment_tab_minings) {

    override val vm: MiningViewModel by sharedViewModel()


}