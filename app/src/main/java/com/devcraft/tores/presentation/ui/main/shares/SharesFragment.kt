package com.devcraft.tores.presentation.ui.main.shares

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.shares.buyShares.BuySharesFragment
import com.devcraft.tores.presentation.ui.main.shares.sharesHistory.SharesHistoryFragment
import com.devcraft.tores.presentation.ui.main.shares.transferShares.TransferSharesFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_shares.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SharesFragment : BaseFragment(R.layout.fragment_shares) {

    override val vm: SharesViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.shares))
        hideBaseActivityBackButton()

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()

        swipeRefresh.setOnRefreshListener {
            vm.refreshData()
        }
        btnSharesHistory.setSafeOnClickListener {
            openFragment(R.id.container, SharesHistoryFragment())
        }
        btnBuyShares.setSafeOnClickListener {
            openFragment(R.id.container, BuySharesFragment())
        }
        btnTransferShares.setSafeOnClickListener {
            openFragment(R.id.container, TransferSharesFragment())
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
        vm.sharesTotalInfo.observe(viewLifecycleOwner, {
            tvLoadingDataError.setGone()
            cvSharesTotalInfo.setVisible()

            btnSharesHistory.setVisible()
            btnBuyShares.setVisible()
            btnTransferShares.setVisible()

            val available = it.availableShares.replace(" ", "").toInt()
            val total = it.totalShares.replace(" ", "").toInt()
            val progress = 100 - (100 * available / total)
            pbShares.progress = progress

            tvSharesBalance.text = String.format(
                resources.getString(R.string.shares_amount_str),
                it.myBalance
            )

            tvSharesAvailable.text =
                getString(R.string.shares_available_from_to, it.availableShares, it.totalShares)
            tvTotalShares.text = it.totalShares
        })
        vm.onGetSharesTotalInfoFailure.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible()
            cvSharesTotalInfo.setGone()

            btnSharesHistory.setGone()
            btnBuyShares.setGone()
            btnTransferShares.setGone()

            showToast(it)
        })
    }
}