package com.devcraft.tores.presentation.ui.main.mining

import android.os.Bundle
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.base.BaseInfoDialog
import com.devcraft.tores.presentation.ui.main.finances.FinancesFragment
import com.devcraft.tores.presentation.ui.main.finances.TabList
import com.devcraft.tores.presentation.ui.main.mining.addToMining.AddToMiningFragment
import com.devcraft.tores.presentation.ui.main.mining.withdrawFromMining.WithdrawFromMiningFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_mining.*
import kotlinx.android.synthetic.main.fragment_mining.swipeRefresh
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MiningFragment : BaseFragment(R.layout.fragment_mining) {

    override val vm: MiningViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.mining))
        hideBaseActivityBackButton()

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()

        swipeRefresh.setOnRefreshListener {
            vm.refreshData()
        }
        btnMiningHistory.setSafeOnClickListener {
            openFragment(R.id.container, FinancesFragment().apply {
                arguments = Bundle().apply {
                    putString(FinancesFragment.ARG_OPEN_FRAGMENT, TabList.MINING.name)
                }
            })
        }
        btnAddToMining.setSafeOnClickListener {
            openFragment(R.id.container, AddToMiningFragment())
        }
        btnWithdrawFromMining.setSafeOnClickListener {
            openFragment(R.id.container, WithdrawFromMiningFragment())
        }
        flTotalInMiningInfo.setSafeOnClickListener {
            openDialog(BaseInfoDialog(getString(R.string.total_in_mining_info_popup)))
        }
        flDailyIncomeInfo.setSafeOnClickListener {
            openDialog(BaseInfoDialog(getString(R.string.daily_income_info_popup)))
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
        vm.miningInfo.observe(viewLifecycleOwner, {
            tvLoadingDataError.setGone()
            cvMiningMainInfo.setVisible()
            btnMiningHistory.setVisible()
            btnAddToMining.setVisible()
            btnWithdrawFromMining.setVisible()

            tvTotalInMining.text = String.format(
                resources.getString(R.string.tores_amount),
                it.myDeposit
            )

            tvDailyIncome.text = String.format(
                resources.getString(R.string.tores_amount),
                it.myDeposit * 0.01
            )

        })
        vm.onGetMiningInfoFailure.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible()
            cvMiningMainInfo.setGone()
            btnMiningHistory.setGone()
            btnAddToMining.setGone()
            btnWithdrawFromMining.setGone()

            showToast(it)
        })
    }
}