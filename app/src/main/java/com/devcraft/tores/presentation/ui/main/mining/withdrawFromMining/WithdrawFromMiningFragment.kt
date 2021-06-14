package com.devcraft.tores.presentation.ui.main.mining.withdrawFromMining

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.mining.MiningViewModel
import com.devcraft.tores.utils.extensions.*
import com.devcraft.tores.utils.inputFilters.InputFilterMinMaxDec
import kotlinx.android.synthetic.main.fragment_withdraw_from_mining.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class WithdrawFromMiningFragment : BaseFragment(R.layout.fragment_withdraw_from_mining) {

    override val vm: WithdrawFromMiningViewModel by viewModel()
    private val vmMining: MiningViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.withdrawal_from_mining))
        showBaseActivityBackButton()
        attachKeyboardListener()
    }

    override fun onDetach() {
        super.onDetach()
        hideKeyboard()
        detachKeyboardListener()
    }

    @SuppressLint("SetTextI18n")
    override fun initListeners() {
        super.initListeners()

        swipeRefresh.setOnRefreshListener {
            vmMining.refreshData()
        }
        tietToresToWithdraw.doAfterTextChanged {
            btnWithdrawFromMining.isEnabled = !it.isNullOrEmpty()
            val needToScrollToBottom = llToresIn.visibility == View.GONE && !it.isNullOrEmpty()
            llToresIn.setVisible(!it.isNullOrEmpty())
            if (needToScrollToBottom) {
                nsv.post {
                    nsv.fullScroll(View.FOCUS_DOWN)
                    tietToresToWithdraw.requestFocus()
                    tietToresToWithdraw.setSelection(tietToresToWithdraw.text.toString().length)
                }
            }

            vmMining.miningInfo.value?.let { mInfo ->
                val enteredTores = tietToresToWithdraw.doubleValue()
                val newDeposit = mInfo.myDeposit - enteredTores
                tvToresAfterWithdraw.text = getString(R.string.tores_amount, newDeposit)
                tvToresInDayAfterWithdraw.text =
                    getString(R.string.tores_amount, newDeposit * 0.01)
                tvToresInYearAfterWithdraw.text =
                    getString(R.string.tores_amount, newDeposit * 0.01 * 365)
            }
        }
        flToresToWithdrawMax.setSafeOnClickListener {
            vmMining.miningInfo.value?.let { mInfo ->
                tietToresToWithdraw.setText(mInfo.myDeposit.toString())
            }
        }
        btnWithdrawFromMining.setSafeOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(
                getString(
                    R.string.are_you_sure_to_withdraw_tores,
                    tietToresToWithdraw.doubleValue()
                )
            )
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    vm.withdrawFromMining(tietToresToWithdraw.doubleValue())
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
            val d = builder.create()
            d.setOnShowListener {
                d.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.colorGreen))
                d.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.colorRed))
            }
            d.show()
        }
    }

    override fun initObservers() {
        super.initObservers()
        vm.someProcessAlive.combineWith(vmMining.someProcessAlive) { v1, v2 -> v1 == true || v2 == true }
            .observe(viewLifecycleOwner, {
                if (swipeRefresh.isRefreshing && !it) {
                    swipeRefresh.isRefreshing = it
                }
                progress_overlay.setVisible(it)
            })

        vmMining.miningInfo.observe(viewLifecycleOwner, { mInfo ->
            tvLoadingDataError.setGone()
            llWithdrawFromMining.setVisible()

            tvAmountToresInMining.text =
                getString(R.string.tores_amount, mInfo.myDeposit)

            tietToresToWithdraw.filters =
                arrayOf(InputFilterMinMaxDec(0.0, mInfo.myDeposit))
        })
        vmMining.onGetMiningInfoFailure.observe(viewLifecycleOwner, {
            llWithdrawFromMining.setGone()
            tvLoadingDataError.setVisible()
            showToast(it)
        })
        vm.onWithdrawFromMiningSuccess.observe(viewLifecycleOwner, {
            showToast(getString(R.string.successfully_withdraw_from_mining))
            vmMining.refreshData()
            onBackPressed()
        })
        vm.onWithdrawFromMiningFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })

    }
}