package com.devcraft.tores.presentation.ui.auth

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : BaseActivity(R.layout.activity_auth) {

    override val vm: AuthViewModel by viewModel()

    override fun initViews() {
        openFragment(R.id.container, LogInFragment(), false)
    }
}