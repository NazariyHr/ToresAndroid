package com.devcraft.tores.presentation.ui.main.profile.financePassword

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.profile.ProfileViewModel
import com.devcraft.tores.utils.extensions.combineWith
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import com.devcraft.tores.utils.functions.checkAllIsNotEmptyAndSetError
import com.devcraft.tores.utils.functions.setClearErrorAfterTextChanged
import kotlinx.android.synthetic.main.fragment_finance_password.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FinancePasswordFragment : BaseFragment(R.layout.fragment_finance_password) {

    override val vm: FinancePasswordViewModel by viewModel()
    private val vmProfile: ProfileViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.finance_password))
        showBaseActivityBackButton()
    }

    override fun initListeners() {
        super.initListeners()

        swipeRefresh.setOnRefreshListener {
            vmProfile.refreshData()
        }
        setClearErrorAfterTextChanged(
            mapOf(
                tietPassword to tilPassword,
                tietPasswordConfirm to tilPasswordConfirm
            )
        )
        btnSetFinancePassword.setSafeOnClickListener {
            val allDataIsNotEntered = checkAllIsNotEmptyAndSetError(
                mapOf(
                    tietPassword to tilPassword,
                    tietPasswordConfirm to tilPasswordConfirm
                ), getString(R.string.fill_the_field)
            )
            if (allDataIsNotEntered) {
                return@setSafeOnClickListener
            }

            val pass = tietPassword.text.toString()
            val passConfirm = tietPasswordConfirm.text.toString()

            vm.setFinancePassword(pass, passConfirm)
        }
        btnRemoveFinancePassword.setSafeOnClickListener {
            vm.removeFinancePassword()
        }
    }

    override fun initObservers() {
        super.initObservers()

        vm.someProcessAlive.combineWith(vmProfile.someProcessAlive) { v1, v2 ->
            v1 == true || v2 == true
        }.observe(viewLifecycleOwner, {
            if (swipeRefresh.isRefreshing && !it) {
                swipeRefresh.isRefreshing = it
            }
            progress_overlay.setVisible(it)
        })
        vmProfile.userInfo.observe(viewLifecycleOwner, {
            llContent.setVisible()
            tvLoadingDataError.setGone()

            tvFinancePasswordIsSet.setVisible(it.financePasswordSet)
            tvFinancePasswordIsNotSet.setVisible(!it.financePasswordSet)
            llSetFinancePassword.setVisible(!it.financePasswordSet)
            llRemoveFinancePassword.setVisible(it.financePasswordSet)
        })
        vmProfile.onGetUserFailure.observe(viewLifecycleOwner, {
            llContent.setGone()
            tvLoadingDataError.setVisible()

            showToast(it)
        })
        vm.setFinancePasswordSuccess.observe(viewLifecycleOwner, {
            if (it) {
                showToast(getString(R.string.finance_password_successfully_set))
                vmProfile.refreshData()
            }
        })
        vm.onSetFinancePasswordFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
        vm.removeFinancePasswordSuccess.observe(viewLifecycleOwner, {
            showToast(it)
        })
        vm.onRemoveFinancePasswordFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }
}