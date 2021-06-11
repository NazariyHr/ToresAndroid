package com.devcraft.tores.presentation.ui.auth

import android.util.Log
import com.devcraft.tores.R
import com.devcraft.tores.app.Constants
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.MainActivity
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import com.devcraft.tores.utils.functions.checkAllIsNotEmptyAndSetError
import com.devcraft.tores.utils.functions.setClearErrorAfterTextChanged
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LogInFragment : BaseFragment(R.layout.fragment_login) {

    companion object {
        const val TAG = "LogInFragment"
    }

    override val vm: AuthViewModel by sharedViewModel()

    override fun initListeners() {
        tvGoToResetPassword.setSafeOnClickListener {
            openFragment(R.id.container, ResetPasswordFragment())
        }

        tvGoToRegister.setSafeOnClickListener {
            openFragment(R.id.container, RegisterFragment())
        }

        setClearErrorAfterTextChanged(
            mapOf(
                tietLogin to tilLogin,
                tietPassword to tilPassword
            )
        )

        btnAccept.setSafeOnClickListener {
            val allDataIsNotEntered = checkAllIsNotEmptyAndSetError(
                mapOf(
                    tietLogin to tilLogin,
                    tietPassword to tilPassword
                ), getString(R.string.fill_the_field)
            )
            if (allDataIsNotEntered) {
                return@setSafeOnClickListener
            }

            val login = tietLogin.text.toString()
            val password = tietPassword.text.toString()

            vm.logIn(login, password, "")

//            SafetyNet.getClient(requireActivity()).verifyWithRecaptcha(Constants.RECAPTCHA_SITE_KEY)
//                .addOnSuccessListener { response ->
//                    // Indicates communication with reCAPTCHA service was successful.
//                    val userResponseToken = response.tokenResult
//                    if (response.tokenResult?.isNotEmpty() == true) {
//                        // Validate the user response token using the
//                        // reCAPTCHA site verify API.
//                        vm.logIn(login, password, userResponseToken)
//                    } else {
//                        showToast("Error: recaptcha response token is null")
//                    }
//                }
//                .addOnFailureListener { e ->
//                    if (e is ApiException) {
//                        // An error occurred when communicating with the
//                        // reCAPTCHA service. Refer to the status code to
//                        // handle the error appropriately.
//                        Log.d(TAG, "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}")
//                        showToast("Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}")
//                        openActivity(MainActivity::class.java, true)
//                    } else {
//                        // A different, unknown type of error occurred.
//                        Log.d(TAG, "Error: ${e.message}")
//                        showToast("Error: ${e.message}")
//                        openActivity(MainActivity::class.java, true)
//                    }
//                }
        }
    }

    override fun initObservers() {
        super.initObservers()
        vm.someProcessAlive.observe(viewLifecycleOwner, {
            progress_overlay.setVisible(it)
        })
        vm.onLoginSuccess.observe(viewLifecycleOwner, {
            if (it) {
                showToast(getString(R.string.successfully_authorized))
                openActivity(MainActivity::class.java, true)
            }
        })
        vm.onLoginFailure.observe(viewLifecycleOwner, {
            showToast(it.message.orEmpty())
        })
    }
}