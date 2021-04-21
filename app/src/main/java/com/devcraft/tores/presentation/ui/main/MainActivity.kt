package com.devcraft.tores.presentation.ui.main

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseActivity
import com.devcraft.tores.presentation.ui.main.dashboard.DashBoardFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesFragment
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    override val vm: MainViewModel by viewModel()

    override fun initViews() {
        openFragment(R.id.container, DashBoardFragment(), false)
    }

    override fun initListeners() {
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    if (!item.isChecked) {
                        openFragment(R.id.container, DashBoardFragment())
                    }
                    true
                }
                R.id.finances -> {
                    if (!item.isChecked) {
                        openFragment(R.id.container, FinancesFragment())
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onNetworkConnectivityStatusChanged(isNetworkAvailable: Boolean) {
        super.onNetworkConnectivityStatusChanged(isNetworkAvailable)
        ll_has_no_internet_connection.setVisible(!isNetworkAvailable)
    }
}