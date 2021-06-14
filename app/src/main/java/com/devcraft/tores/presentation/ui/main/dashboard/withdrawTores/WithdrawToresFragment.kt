package com.devcraft.tores.presentation.ui.main.dashboard.withdrawTores

import android.annotation.SuppressLint
import android.view.View
import android.widget.AdapterView
import androidx.core.widget.doAfterTextChanged
import com.devcraft.tores.R
import com.devcraft.tores.entities.Currency
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.dashboard.currencyListAdapter.CurrencyListAdapter
import com.devcraft.tores.presentation.ui.main.dashboard.currencyListAdapter.DH
import com.devcraft.tores.utils.extensions.doubleValue
import com.devcraft.tores.utils.extensions.round
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_withdraw_tores.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal
import java.math.RoundingMode

class WithdrawToresFragment : BaseFragment(R.layout.fragment_withdraw_tores) {

    override val vm: WithdrawToresViewModel by viewModel()

    private lateinit var adapter: CurrencyListAdapter

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.do_withdraw_title))
        showBaseActivityBackButton()
        attachKeyboardListener()
        initCurrenciesList()

        vm.loadData()
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
            vm.refreshData()
        }
        actvCurrency.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selected = adapter.getItem(position)
                selected?.let {
                    onCurrencySelected(it)
                }
            }
        tietWithdrawSum.doAfterTextChanged {
            btnTransferToExchange.isEnabled =
                !it.isNullOrEmpty() && !tietRequisites.text.isNullOrEmpty()
            val needToScrollToBottom =
                tvObtainAmountTitle.visibility == View.GONE && !it.isNullOrEmpty()
            tvObtainAmountTitle.setVisible(!it.isNullOrEmpty())
            tvObtainAmount.setVisible(!it.isNullOrEmpty())
            if (needToScrollToBottom) {
                nsv.post {
                    nsv.fullScroll(View.FOCUS_DOWN)
                    tietWithdrawSum.requestFocus()
                    tietWithdrawSum.setSelection(tietWithdrawSum.text.toString().length)
                }
            }

            recalculateObtainAmount()
        }
        flWithdrawSumMax.setSafeOnClickListener {
            vm.miningInfo.value?.let { mInfo ->
                tietWithdrawSum.setText(mInfo.myBalance.toString())
            }
        }
        tietRequisites.doAfterTextChanged {
            btnTransferToExchange.isEnabled =
                !it.isNullOrEmpty() && !tietWithdrawSum.text.isNullOrEmpty()
        }
        btnTransferToExchange.setSafeOnClickListener {
            vm.withdraw(tietWithdrawSum.doubleValue(), tietRequisites.text.toString())
        }
    }

    override fun initObservers() {
        super.initObservers()

        vm.someProcessAlive.observe(viewLifecycleOwner, {
            if (swipeRefresh.isRefreshing && !it) {
                swipeRefresh.isRefreshing = it
            }
            progress_overlay.setVisible(it)
        })
        vm.allProcessesAreSuccess.observe(viewLifecycleOwner, {
            llContent.setVisible(it)
        })
        vm.allProcessesAreFailed.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible(it)
        })
        vm.onGetCurrencyRatesFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
        vm.onGetMiningInfoFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
        vm.onWithdrawSuccess.observe(viewLifecycleOwner, {
            showToast(getString(R.string.successfully_withdraw))
            onBackPressed()
        })
        vm.onWithdrawFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }

    private fun initCurrenciesList() {
        val items = Currency.getCurrenciesForWithdraw().map { DH(it) }.toMutableList()
        adapter = CurrencyListAdapter(requireContext(), items)
        actvCurrency?.setAdapter(adapter)

        if (actvCurrency.text.toString().isEmpty()) {
            items.first().let {
                actvCurrency.setText(it.toString(), false)
                onCurrencySelected(it)
            }
        } else {
            vm.selectedCurrencyToWithdraw?.let {
                val refreshedDh = DH(it)
                actvCurrency.setText(refreshedDh.toString(), false)
            }
        }
    }

    private fun onCurrencySelected(dh: DH) {
        vm.selectedCurrencyToWithdraw = dh.currency
        recalculateObtainAmount()
    }

    private fun recalculateObtainAmount() {
        vm.selectedCurrencyToWithdraw?.let { currency ->
            vm.currencyRatesInfo.value?.let { ratesInfo ->
                val enteredSum = BigDecimal(tietWithdrawSum.doubleValue())
                val toresToCurrencyWithoutFee =
                    enteredSum.subtract(enteredSum.times(BigDecimal(0.05)))
                val cRate = ratesInfo.currencyRates.getOrElse(currency) { 1.0 }
                val resultSumInCurrency = toresToCurrencyWithoutFee.divide(BigDecimal(cRate), 6, RoundingMode.HALF_UP)
                tvObtainAmount.text =
                    getString(
                        R.string.amount_in_currency,
                        resultSumInCurrency.round(6),
                        currency.getTitle()
                    )
            }
        }
    }
}