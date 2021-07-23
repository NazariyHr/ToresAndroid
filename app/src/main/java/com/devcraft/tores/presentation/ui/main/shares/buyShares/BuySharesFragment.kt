package com.devcraft.tores.presentation.ui.main.shares.buyShares

import androidx.core.widget.doAfterTextChanged
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.shares.SharesViewModel
import com.devcraft.tores.utils.extensions.*
import com.devcraft.tores.utils.inputFilters.InputFilterMinMaxInt
import kotlinx.android.synthetic.main.fragment_buy_shares.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BuySharesFragment : BaseFragment(R.layout.fragment_buy_shares) {

    override val vm: BuySharesViewModel by viewModel()
    private val vmShares: SharesViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.do_buy_shares_title))
        showBaseActivityBackButton()
        attachKeyboardListener()
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
            vmShares.refreshData()
        }
        tietBuySum.doAfterTextChanged {
            btnBuy.isEnabled = !it.isNullOrEmpty()
        }
        btnBuy.setSafeOnClickListener {
            vm.buy(tietBuySum.intValue())
        }
    }

    override fun initObservers() {
        super.initObservers()
        vmShares.someProcessAlive.combineWith(vm.someProcessAlive) { v1, v2 ->
            v1 == true || v2 == true
        }.observe(viewLifecycleOwner, {
            if (swipeRefresh.isRefreshing && !it) {
                swipeRefresh.isRefreshing = it
            }
            progress_overlay.setVisible(it)
        })
        vmShares.sharesTotalInfo.observe(viewLifecycleOwner, {
            tvBonusBalance.text = getString(R.string.tores_amount_str, it.myRankBalance)
            val availToBuy = it.myRankBalance.toDouble() / it.oneSharePrice.toDouble()
            tvAvailableToBuy.text =
                getString(R.string.shares_amount_str, availToBuy.toInt().toString())
            tietBuySum.filters =
                arrayOf(InputFilterMinMaxInt(0, availToBuy.toInt()))

        })
        vmShares.allProcessesAreSuccess.observe(viewLifecycleOwner, {
            llContent.setVisible(it)
        })
        vmShares.allProcessesAreFailed.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible(it)
        })
        vm.onBuySuccess.observe(viewLifecycleOwner, {
            showToast(getString(R.string.shares_successfully_bought))
            vmShares.refreshData()
            onBackPressed()
        })
        vm.onBuyFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }
}
