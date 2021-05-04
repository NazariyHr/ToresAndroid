package com.devcraft.tores.presentation.ui.main.finances

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.finances.rankProfits.RankProfitsTabFragment
import com.devcraft.tores.presentation.ui.main.finances.mining.MiningsTabFragment
import com.devcraft.tores.presentation.ui.main.finances.partnersProfits.PartnersProfitsTabFragment
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.TopupsAndWithdrawalsTabFragment
import com.devcraft.tores.presentation.ui.main.finances.transfers.TransfersTabFragment
import kotlinx.android.synthetic.main.fragment_finances.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FinancesFragment : BaseFragment(R.layout.fragment_finances), TabTitlesAdapter.Callback {

    override val vm: FinancesViewModel by sharedViewModel()

    private val adapter: TabTitlesAdapter = TabTitlesAdapter()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.finances))
        rvTabTitles.adapter = adapter
        rvTabTitles.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun initListeners() {
        super.initListeners()
        adapter.callback = this
    }

    override fun onTabClicked(tab: TabList) {
        when (tab) {
            TabList.TOPUPS_AND_WITHDRAWAL -> showTopupsAndWithdrawals()
            TabList.MINING -> showMiningTab()
            TabList.TRANSFERS -> showTransfersTab()
            TabList.PARTNERS -> showPartnersRewardsTab()
            TabList.BONUSES -> showBonusRewardsTab()
        }
    }

    private fun showTopupsAndWithdrawals() {
        openFragment(R.id.containerFinancesTabs, TopupsAndWithdrawalsTabFragment(), false)
    }

    private fun showMiningTab() {
        openFragment(R.id.containerFinancesTabs, MiningsTabFragment(), false)
    }

    private fun showTransfersTab() {
        openFragment(R.id.containerFinancesTabs, TransfersTabFragment(), false)
    }

    private fun showPartnersRewardsTab() {
        openFragment(R.id.containerFinancesTabs, PartnersProfitsTabFragment(), false)
    }

    private fun showBonusRewardsTab() {
        openFragment(R.id.containerFinancesTabs, RankProfitsTabFragment(), false)
    }
}