package com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.transactionDetails

import android.annotation.SuppressLint
import android.os.CountDownTimer
import com.bumptech.glide.Glide
import com.devcraft.tores.R
import com.devcraft.tores.entities.Currency
import com.devcraft.tores.entities.PaymentConfirmationWay
import com.devcraft.tores.entities.TopupsAndWithdrawalsData
import com.devcraft.tores.entities.TransactionStatus
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesViewModel
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.transactionDetails.topupPayment.TopupPaymentFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import com.devcraft.tores.utils.functions.checkAllIsNotEmptyAndSetError
import com.devcraft.tores.utils.functions.initCountdownTimerWithWithTimeFormattingTick
import com.devcraft.tores.utils.functions.setClearErrorAfterTextChanged
import kotlinx.android.synthetic.main.fragment_transaction_details.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TransactionDetailsFragment : BaseFragment(R.layout.fragment_transaction_details) {
    override val vm: TransactionDetailsViewModel by sharedViewModel()
    private val vmFinances: FinancesViewModel by sharedViewModel()

    private var timer: CountDownTimer? = null

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
        vm.onCancelTopupSuccess.observe(viewLifecycleOwner, {
            showToast(it)
            vmFinances.needRefreshTopupsAndWithdrawals.postValue(true)
            vm.refreshData()
        })
        vm.onTacSubmittedSuccess.observe(viewLifecycleOwner, {
            showToast(it)
            vmFinances.needRefreshTopupsAndWithdrawals.postValue(true)
            vm.refreshData()
        })
        vm.onCancelTopupFailure.observe(viewLifecycleOwner, {
            showToast(it.message.orEmpty())
        })

        vm.selectedTransaction.observe(viewLifecycleOwner, { t ->
            llContent.setVisible(t != null)
            if (t != null) {
                when (t.type) {
                    TopupsAndWithdrawalsData.Transaction.Type.TOPUP -> {
                        tvTransactionTitle.text = getString(R.string.topup)
                        tvTopupSubtitle.setVisible()
                        ivQr.setVisible(!t.qr.isNullOrEmpty())
                        if (!t.qr.isNullOrEmpty()) {
                            Glide.with(requireContext())
                                .load(t.qr)
                                .into(ivQr)
                        }
                        tvPaymentSumTitle.text = getString(R.string.topup_details_payment_sum)
                        llConfirmations.setVisible(
                            t.transactionStatus == TransactionStatus.CREATED
                                    && t.confirmationsNeeded != 0
                        )
                        tvConfirmations.text = "${t.confirmationsTotal}/${t.confirmationsNeeded}"

                        if (t.transactionStatus == TransactionStatus.CREATED && t.remaining > 0) {
                            llTimeLeft.setVisible()
                            tvWaitingTime.text = ""
                            timer?.cancel()
                            initCountdownTimerWithWithTimeFormattingTick(
                                t.remaining * 1000, 1000, { formattedTime ->
                                    tvWaitingTime?.text = formattedTime
                                }, {
                                    tvWaitingTime?.text =
                                        requireContext().getString(R.string.time_is_over)
                                }, { newTimer ->
                                    timer = newTimer
                                }
                            )
                        } else {
                            llTimeLeft.setGone()
                        }

                        btnGoToPay.setVisible(
                            t.transactionStatus == TransactionStatus.CREATED
                                    && (t.currency == Currency.PERFECTMONEY ||
                                    t.currency == Currency.PAYEER)
                        )
                        btnGoToPay.setSafeOnClickListener {
                            openFragment(R.id.container, TopupPaymentFragment())
                        }

                        btnCancel.setVisible(t.transactionStatus == TransactionStatus.CREATED)
                        btnCancel.setSafeOnClickListener {
                            vm.cancelTopup(t.id)
                        }
                        llEnterTac.setGone()
                    }
                    TopupsAndWithdrawalsData.Transaction.Type.WITHDRAWAL -> {
                        tvTransactionTitle.text = getString(R.string.withdrawal)
                        tvTopupSubtitle.setGone()
                        ivQr.setGone()
                        tvPaymentSumTitle.text = getString(R.string.withdrawal_details_payment_sum)
                        llConfirmations.setGone()
                        llTimeLeft.setGone()
                        btnGoToPay.setGone()
                        btnCancel.setGone()

                        if (t.transactionStatus == TransactionStatus.WAITING_TAC) {
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
                }

                tvTitleTransactionId.text = t.id.toString()
                tvId.text = t.id.toString()
                tvDateAndTime.text = t.createdAt
                tvWallet.text = t.wallet
                t.currency.getCurrencyDrawable(requireContext()).let { d ->
                    if (d == null) {
                        ivCurrency.setGone()
                    } else {
                        ivCurrency.setVisible()
                        ivCurrency.setImageDrawable(d)
                    }
                }
                tvPaymentSum.text = t.amountInCurrency.toString()
                tvSumInTores.text =
                    String.format(resources.getString(R.string.tores_amount), t.amount)

                tvStatus.setTextColor(t.transactionStatus.getStatusColor(requireContext()))
                tvStatus.text = t.transactionStatus.getStatusText(requireContext(), true)
            }
        })

        vm.confirmationWay.observe(viewLifecycleOwner, { way ->
            vm.selectedTransaction.value?.let { t ->
                llPasswordFromEmail.setVisible(way == PaymentConfirmationWay.EMAIL)
                tvPp.setVisible(way == PaymentConfirmationWay.PAYMENT_PASSWORD)

                btnAcceptTac.setSafeOnClickListener {
                    val allDataIsNotEntered = checkAllIsNotEmptyAndSetError(
                        mapOf(
                            tietPassword to tilPassword
                        ), "Заполните поле"
                    )
                    if (allDataIsNotEntered) {
                        return@setSafeOnClickListener
                    }

                    vm.submitTac(t.id, tietPassword.text.toString())
                }
            }
        })
    }
}