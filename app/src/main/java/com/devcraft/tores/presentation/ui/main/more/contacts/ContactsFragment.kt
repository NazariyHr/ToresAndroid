package com.devcraft.tores.presentation.ui.main.more.contacts

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.MainViewModel
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    override val vm: MainViewModel by viewModel()

    override fun initListeners() {
        llTelegramToresCoin.setSafeOnClickListener {
            openTelegram(tvTelegramMain.text.toString())
        }
        llTelegramToresCoinSupport.setSafeOnClickListener {
            openTelegram(tvTelegramSupport.text.toString())
        }
        llEmail.setSafeOnClickListener {
            openEmail(tvEmail.text.toString())
        }
    }
}