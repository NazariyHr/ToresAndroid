package com.devcraft.tores.presentation.ui.auth

import android.annotation.SuppressLint
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    override val vm: AuthViewModel by sharedViewModel()

    @SuppressLint("SimpleDateFormat")
    override fun initListeners() {
        tvGoToLogIn.setSafeOnClickListener {
            goBackToLogin()
        }

        btnDoRegister.setSafeOnClickListener {
            showToast(getString(R.string.in_development))
        }
    }

    override fun initObservers() {
        super.initObservers()
        vm.someProcessAlive.observe(viewLifecycleOwner, {
            progress_overlay.setVisible(it)
        })
    }

    private fun goBackToLogin() {
        requireActivity().onBackPressed()
    }
}