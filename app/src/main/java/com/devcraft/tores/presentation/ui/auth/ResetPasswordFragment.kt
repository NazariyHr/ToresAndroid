package com.devcraft.tores.presentation.ui.auth

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_reset_password.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ResetPasswordFragment : BaseFragment(R.layout.fragment_reset_password) {

    override val vm: AuthViewModel by sharedViewModel()

    override fun initListeners() {
        btnReset.setSafeOnClickListener {
            showToast("В разработке")
        }
    }
}