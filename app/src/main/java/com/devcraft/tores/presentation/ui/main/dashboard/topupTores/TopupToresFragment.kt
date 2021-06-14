package com.devcraft.tores.presentation.ui.main.dashboard.topupTores

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
import kotlinx.android.synthetic.main.fragment_topup_tores.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal
import java.math.RoundingMode

class TopupToresFragment : BaseFragment(R.layout.fragment_topup_tores) {

    override val vm: TopupToresViewModel by viewModel()

    private lateinit var adapter: CurrencyListAdapter

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.do_topup_title))
        showBaseActivityBackButton()
        attachKeyboardListener()
        initCurrenciesList()

        vm.loadData()
    }

    override fun onDetach() {
        super.onDetach()
        hideKeyboard()
        detachKeyboardListener()
        getBaseActivity()?.showBottomBar()
    }

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
        tietTopupSum.doAfterTextChanged {
            btnTopup.isEnabled = !it.isNullOrEmpty()
            val needToScrollToBottom =
                tvSumToPayTitle.visibility == View.GONE && !it.isNullOrEmpty()
            tvSumToPayTitle.setVisible(!it.isNullOrEmpty())
            tvSumToPay.setVisible(!it.isNullOrEmpty())
            if (needToScrollToBottom) {
                nsv.post {
                    nsv.fullScroll(View.FOCUS_DOWN)
                    tietTopupSum.requestFocus()
                    tietTopupSum.setSelection(tietTopupSum.text.toString().length)
                }
            }
            recalculateObtainAmount()
        }
        btnTopup.setSafeOnClickListener {
            vm.topup(tietTopupSum.doubleValue())
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
        vm.onTopupSuccess.observe(viewLifecycleOwner, {
            showToast(getString(R.string.successfully_topup))
            onBackPressed()
        })
        vm.onTopupFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }

    private fun initCurrenciesList() {
        val items = Currency.getCurrenciesForTopup().map { DH(it) }.toMutableList()
        adapter = CurrencyListAdapter(requireContext(), items)
        actvCurrency?.setAdapter(adapter)

        if (actvCurrency.text.toString().isEmpty()) {
            items.first().let {
                actvCurrency.setText(it.toString(), false)
                onCurrencySelected(it)
            }
        } else {
            vm.selectedCurrencyToTopup?.let {
                val refreshedDh = DH(it)
                actvCurrency.setText(refreshedDh.toString(), false)
            }
        }
    }

    private fun onCurrencySelected(dh: DH) {
        vm.selectedCurrencyToTopup = dh.currency
        recalculateObtainAmount()
    }

    private fun recalculateObtainAmount() {
        vm.selectedCurrencyToTopup?.let { currency ->
            vm.currencyRatesInfo.value?.let { ratesInfo ->
                val enteredSum = BigDecimal(tietTopupSum.doubleValue())
                val cRate = ratesInfo.currencyRates.getOrElse(currency) { 1.0 }
                val sumToPayInCurrency =
                    enteredSum.divide(BigDecimal(cRate), 6, RoundingMode.HALF_UP)
                tvSumToPay.text =
                    getString(
                        R.string.amount_in_currency,
                        sumToPayInCurrency.round(6),
                        currency.getTitle()
                    )
            }
        }
    }
}