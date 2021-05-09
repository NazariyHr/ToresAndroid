package com.devcraft.tores.presentation.ui.main.finances

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devcraft.tores.presentation.ui.main.finances.mining.MiningsTabFragment
import com.devcraft.tores.presentation.ui.main.finances.partnersProfits.PartnersProfitsTabFragment
import com.devcraft.tores.presentation.ui.main.finances.rankProfits.RankProfitsTabFragment
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.TopupsAndWithdrawalsTabFragment
import com.devcraft.tores.presentation.ui.main.finances.transfers.TransfersTabFragment

class TabsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val tabs = listOf(
        TabList.TOPUPS_AND_WITHDRAWAL,
        TabList.MINING,
        TabList.TRANSFERS,
        TabList.PARTNERS,
        TabList.BONUSES
    )

    override fun createFragment(position: Int): Fragment {
        return when (tabs[position]) {
            TabList.TOPUPS_AND_WITHDRAWAL -> TopupsAndWithdrawalsTabFragment()
            TabList.MINING -> MiningsTabFragment()
            TabList.TRANSFERS -> TransfersTabFragment()
            TabList.PARTNERS -> PartnersProfitsTabFragment()
            TabList.BONUSES -> RankProfitsTabFragment()
        }
    }

    override fun getItemCount(): Int = tabs.size

    fun getTabByPosition(position: Int) = tabs[position]

    fun getPositionByTab(tab: TabList) = tabs.indexOf(tab)
}