package com.devcraft.tores.presentation.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseActivity
import com.devcraft.tores.presentation.ui.main.dashboard.DashBoardFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesFragment
import com.devcraft.tores.presentation.ui.main.mining.MiningFragment
import com.devcraft.tores.presentation.ui.main.more.MoreFragment
import com.devcraft.tores.presentation.ui.main.profile.ProfileFragment
import com.devcraft.tores.utils.PackageManager
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

    override fun onShowKeyboard() {
        hideBottomBar()
    }

    override fun onHideKeyboard() {
        showBottomBar()
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
        } else if (fragment is MiningFragment) {
            openFragment(container, fragment, addToBackStack)
            val item: MenuItem = bottom_navigation.menu.findItem(R.id.mining)
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
        flContacts.setSafeOnClickListener {
            showStatusPopup()
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
                R.id.mining -> {
                    if (!item.isChecked) {
                        openFragment(R.id.container, MiningFragment())
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

    @SuppressLint("RestrictedApi")
    private fun showStatusPopup() {
        val menuBuilder = MenuBuilder(this)
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.popup_contacts, menuBuilder)
        val optionsMenu = MenuPopupHelper(this, menuBuilder, flContacts)
        optionsMenu.setForceShowIcon(true)

        menuBuilder.setCallback(object : MenuBuilder.Callback {
            override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.telegram_main -> {
                        openTelegram(getString(R.string.telegram_tores_coin))
                        true
                    }
                    R.id.telegram_main_support -> {
                        openTelegram(getString(R.string.telegram_tores_coin_support))
                        true
                    }
                    R.id.email -> {
                        openEmail(getString(R.string.tores_email))
                        true
                    }
                    else -> false
                }
            }

            override fun onMenuModeChange(menu: MenuBuilder) {}
        })
        optionsMenu.show()
    }

    fun openTelegram(channelName: String) {
        val appName = "org.telegram.messenger"
        if (PackageManager.isAppAvailable(appName)) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=$channelName"))
            startActivity(intent)
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$channelName"))
            startActivity(browserIntent)
        }
    }

    fun openEmail(text: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(text))
        openActivity(Intent.createChooser(emailIntent, "Send mail..."), false)
    }
}
