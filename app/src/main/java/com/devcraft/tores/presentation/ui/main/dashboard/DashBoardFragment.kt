package com.devcraft.tores.presentation.ui.main.dashboard

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.dashboard.topupTores.TopupToresFragment
import com.devcraft.tores.presentation.ui.main.dashboard.transferTo.TransferToFragment
import com.devcraft.tores.presentation.ui.main.dashboard.withdrawTores.WithdrawToresFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesFragment
import com.devcraft.tores.presentation.ui.main.finances.TabList
import com.devcraft.tores.presentation.ui.main.mining.MiningFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashBoardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val vm: DashBoardViewModel by viewModel()

    private val profitsAdapter: ProfitsAdapter = ProfitsAdapter()
    private val registersAdapter: RegistersAdapter = RegistersAdapter()

    lateinit var selectedDrawable: Drawable

    override fun initViews() {
        setBaseActivityToolbarTitle(getString(R.string.dashboard))
        hideBaseActivityBackButton()

        selectedDrawable = btnByPartners.background

        rvProfits.adapter = profitsAdapter
        rvProfits.layoutManager = LinearLayoutManager(context)

        rvRegistrations.adapter = registersAdapter
        rvRegistrations.layoutManager = LinearLayoutManager(context)

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()
        swipeRefresh.setOnRefreshListener {
            vm.refreshData()
        }
        btnMine.setSafeOnClickListener {
            openFragment(R.id.container, MiningFragment())
        }
        btnTransferTores.setSafeOnClickListener {
            openFragment(R.id.container, TransferToFragment())
        }
        btnBuyTores.setSafeOnClickListener {
            openFragment(R.id.container, TopupToresFragment())
        }
        btnSellTores.setSafeOnClickListener {
            openFragment(R.id.container, WithdrawToresFragment())
        }
        btnBonusBalanceDetails.setSafeOnClickListener {
            openFragment(R.id.container, FinancesFragment())
        }
        btnIncomeForAllTimeDetails.setSafeOnClickListener {
            openFragment(R.id.container, FinancesFragment())
        }
        btnByPartners.setOnClickListener {
            btnByPartners.background = selectedDrawable
            btnByPartners.setTextColor(requireContext().resources.getColor(R.color.black))
            btnByRanks.background = null
            btnByRanks.setTextColor(requireContext().resources.getColor(R.color.white))
            tvRewardsPartners.setVisible()
            tvRewardsRanks.setGone()
        }
        btnByRanks.setOnClickListener {
            btnByRanks.background = selectedDrawable
            btnByRanks.setTextColor(requireContext().resources.getColor(R.color.black))
            btnByPartners.background = null
            btnByPartners.setTextColor(requireContext().resources.getColor(R.color.white))
            tvRewardsRanks.setVisible()
            tvRewardsPartners.setGone()
        }
        btnRewardsDetails.setSafeOnClickListener {
            val args = Bundle()
            if (tvRewardsPartners.visibility == View.VISIBLE) {
                args.putString(FinancesFragment.ARG_OPEN_FRAGMENT, TabList.PARTNERS.name)
            } else {
                args.putString(FinancesFragment.ARG_OPEN_FRAGMENT, TabList.BONUSES.name)
            }
            openFragment(R.id.container, FinancesFragment().apply { arguments = args })
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
        vm.allProcessesAreFailed.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible(it)
        })

        vm.userInfo.observe(viewLifecycleOwner, {
            cvBalance.setVisible()
            cvBonusBalance.setVisible()
            cvIncome.setVisible()
            cvRewards.setVisible()

            tvBalance.text = String.format(resources.getString(R.string.tores_amount), it.balance)
            tvBonusBalance.text =
                String.format(resources.getString(R.string.tores_amount), it.rankBalance)

            tvIncome.text =
                String.format(resources.getString(R.string.tores_amount), it.totalProfit)
            tvRewardsPartners.text =
                String.format(resources.getString(R.string.tores_amount), it.partnerProfit)
            tvRewardsRanks.text =
                String.format(resources.getString(R.string.tores_amount), it.rankProfit)
        })
        vm.onGetUserFailure.observe(viewLifecycleOwner, {
            cvBalance.setGone()
            cvBonusBalance.setGone()
            cvIncome.setGone()
            cvRewards.setGone()

            showToast(it.message.orEmpty())
        })
        vm.profitsAndRegisters.observe(viewLifecycleOwner, {
            cvFiveLastTransactions.setVisible()
            profitsAdapter.items = it.profits
            registersAdapter.items = it.registers
        })

        vm.onGetProfitsAndRegistersFailure.observe(viewLifecycleOwner, {
            cvFiveLastTransactions.setGone()
            showToast(it.message.orEmpty())
        })
    }
}
