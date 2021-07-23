package com.devcraft.tores.presentation.ui.main.finances.transfers.transferDetails

import android.annotation.SuppressLint
import com.devcraft.tores.R
import com.devcraft.tores.entities.*
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesViewModel
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import com.devcraft.tores.utils.functions.checkAllIsNotEmptyAndSetError
import com.devcraft.tores.utils.functions.setClearErrorAfterTextChanged
import kotlinx.android.synthetic.main.fragment_transfer_details.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TransferDetailsFragment : BaseFragment(R.layout.fragment_transfer_details) {
    override val vm: TransferDetailsViewModel by sharedViewModel()
    private val vmFinances: FinancesViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        showBaseActivityBackButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideBaseActivityBackButton()
    }

    override fun initListeners() {
        super.initListeners()

        swipeRefresh.setOnRefreshListener {
            vm.refreshData()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initObservers() {
        super.initObservers()

        vm.someProcessAlive.observe(viewLifecycleOwner, {
            if (swipeRefresh.isRefreshing && !it) {
                swipeRefresh.isRefreshing = it
            }
            progress_overlay.setVisible(it)
        })
        vm.onTacSubmittedSuccess.observe(viewLifecycleOwner, {
            showToast(it)
            vmFinances.needRefreshTransfers.postValue(true)
            vmFinances.needRefreshTopupsAndWithdrawals.postValue(true)
            vm.refreshData()
        })

        vm.selectedTransfer.observe(viewLifecycleOwner, { t ->
            llContent.setVisible(t != null)
            if (t != null) {
                when (t.type) {
                    TransfersHistoryData.Transaction.Type.TRANSFER_TO_USER -> {
                        tvTransactionTitle.text = getString(R.string.sending_to_user)
                        tvTransferInfo.setVisible()
                        llWallet.setGone()
                    }
                    TransfersHistoryData.Transaction.Type.TRANSFER_TO_EXCHANGE -> {
                        tvTransactionTitle.text = getString(R.string.sending_to_exchange)
                        tvTransferInfo.setGone()
                        llWallet.setVisible()
                        tvWallet.text = t.wallet
                    }
                    TransfersHistoryData.Transaction.Type.RECEIVED_FROM_USER -> {
                        tvTransactionTitle.text = getString(R.string.sending_to_user)
                        tvTransferInfo.setGone()
                        llWallet.setGone()
                    }
                }

                tvTitleTransactionId.text = t.id.toString()
                tvId.text = t.id.toString()
                tvDateAndTime.text = t.createdAt
                tvSumInTores.text =
                    String.format(resources.getString(R.string.tores_amount), t.amount)
                tvStatus.setTextColor(t.transactionStatus.getStatusColor(requireContext()))
                tvStatus.text = t.transactionStatus.getStatusText(requireContext(), true)

                if (t.transactionStatus == TransactionStatus.WAITING_TAC &&
                    t.type != TransfersHistoryData.Transaction.Type.RECEIVED_FROM_USER
                ) {
                    llEnterTac.setVisible()
                    setClearErrorAfterTextChanged(
                        mapOf(
                            tietPassword to tilPassword
                        )
                    )
                    vm.loadUserPaymentConfirmWay()
                } else {
                    llEnterTac.setGone()
                }
            }
        })

        vm.confirmationWay.observe(viewLifecycleOwner, { way ->
            vm.selectedTransfer.value?.let { t ->
                llPasswordFromEmail.setVisible(way == PaymentConfirmationWay.EMAIL)
                tvPp.setVisible(way == PaymentConfirmationWay.PAYMENT_PASSWORD)

                when (t.type) {
                    TransfersHistoryData.Transaction.Type.TRANSFER_TO_EXCHANGE -> {
                        tvPasswordFromEmailTitle1.setVisible()
                        tvPasswordFromEmailTitle2.setVisible()
                        tvPasswordFromEmailTitle3.setGone()
                    }
                    else -> {
                        tvPasswordFromEmailTitle1.setGone()
                        tvPasswordFromEmailTitle2.setGone()
                        tvPasswordFromEmailTitle3.setVisible()
                    }
                }

                btnAcceptTac.setSafeOnClickListener {
                    val allDataIsNotEntered = checkAllIsNotEmptyAndSetError(
                        mapOf(
                            tietPassword to tilPassword
                        ), getString(R.string.fill_the_field)
                    )
                    if (allDataIsNotEntered) {
                        return@setSafeOnClickListener
                    }

                    vm.submitTac(t.id, t.type, tietPassword.text.toString())
                }
            }
        })
    }
}