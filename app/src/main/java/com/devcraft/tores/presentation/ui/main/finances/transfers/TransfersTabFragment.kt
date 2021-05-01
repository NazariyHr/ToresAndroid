package com.devcraft.tores.presentation.ui.main.finances.transfers

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TransfersTabFragment : BaseFragment(R.layout.fragment_tab_transfers) {

    override val vm: TransfersViewModel by sharedViewModel()


}