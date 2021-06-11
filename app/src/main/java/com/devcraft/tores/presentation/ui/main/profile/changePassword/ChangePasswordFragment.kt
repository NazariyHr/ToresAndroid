package com.devcraft.tores.presentation.ui.main.profile.changePassword

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.functions.checkAllIsNotEmptyAndSetError
import com.devcraft.tores.utils.functions.setClearErrorAfterTextChanged
import kotlinx.android.synthetic.main.fragment_change_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : BaseFragment(R.layout.fragment_change_password) {

    override val vm: ChangePasswordViewModel by viewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.password_changing))
        showBaseActivityBackButton()
    }

    override fun initListeners() {
        super.initListeners()

        setClearErrorAfterTextChanged(
            mapOf(
                tietOldPassword to tilOldPassword,
                tietNewPassword to tilNewPassword,
                tietNewPasswordConfirm to tilNewPasswordConfirm
            )
        )

        btnChangePassword.setSafeOnClickListener {
            val allDataIsNotEntered = checkAllIsNotEmptyAndSetError(
                mapOf(
                    tietOldPassword to tilOldPassword,
                    tietNewPassword to tilNewPassword,
                    tietNewPasswordConfirm to tilNewPasswordConfirm
                ), getString(R.string.fill_the_field)
            )
            if (allDataIsNotEntered) {
                return@setSafeOnClickListener
            }

            val oldPass = tietOldPassword.text.toString()
            val newPass = tietNewPassword.text.toString()
            val newPassConfirm = tietNewPasswordConfirm.text.toString()

            vm.changePassword(oldPass, newPass, newPassConfirm)
        }
    }

    override fun initObservers() {
        super.initObservers()

        vm.onChangePasswordSuccess.observe(viewLifecycleOwner, {
            showToast(it)
            onBackPressed()
        })
        vm.changePasswordFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }
}
