package com.devcraft.tores.presentation.ui.main

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseActivity
import com.devcraft.tores.presentation.ui.main.dashboard.DashBoardFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesFragment
import com.devcraft.tores.presentation.ui.main.more.MoreFragment
import com.devcraft.tores.presentation.ui.main.profile.ProfileFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    override val vm: MainViewModel by viewModel()

    override fun setToolbarTitle(title: String) {
        tvToolbarTitle.text = title
    }

    override fun showBackButton() {
        ivLogo.setGone()
        flBack.setVisible()
    }

    override fun hideBackButton() {
        ivLogo.setVisible()
        flBack.setGone()
    }

    override fun showMainTopBar() {
        toolbar.setVisible()
    }

    override fun hideMainTopBar() {
        toolbar.setGone()
    }

    override fun showBottomBar() {
        bottom_navigation.setVisible()
    }

    override fun hideBottomBar() {
        bottom_navigation.setGone()
    }

    override fun initViews() {
        openFragment(R.id.container, DashBoardFragment(), false)
    }

    override fun handleOpenFragment(
        container: Int,
        fragment: Fragment,
        addToBackStack: Boolean
    ): Boolean {
        return if (fragment is FinancesFragment) {
            openFragment(container, fragment, addToBackStack)
            val item: MenuItem = bottom_navigation.menu.findItem(R.id.finances)
            item.isChecked = true
            true
        } else {
            super.handleOpenFragment(container, fragment, addToBackStack)
        }
    }

    override fun initListeners() {
        flBack.setOnClickListener {
            onBackPressed()
        }
        flProfile.setSafeOnClickListener {
            var profileAlreadyOpened = false
            supportFragmentManager.fragments.forEach {
                if (it is ProfileFragment) profileAlreadyOpened = true
            }
            if (!profileAlreadyOpened) {
                openFragment(R.id.container, ProfileFragment())
            }
        }
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
                R.id.more -> {
                    if (!item.isChecked) {
                        openFragment(R.id.container, MoreFragment())
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
