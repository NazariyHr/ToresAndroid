package com.devcraft.tores.presentation.ui.main.finances

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FinancesFragment : BaseFragment(R.layout.fragment_finances) {

    override val vm: MainViewModel by sharedViewModel()

}