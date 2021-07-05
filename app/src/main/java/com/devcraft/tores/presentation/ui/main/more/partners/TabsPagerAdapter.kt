package com.devcraft.tores.presentation.ui.main.more.partners

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devcraft.tores.presentation.ui.main.more.partners.ourPartners.OurPartnersTabFragment
import com.devcraft.tores.presentation.ui.main.more.partners.userRequests.UserRequestsTabFragment

class TabsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val tabs = listOf(
        TabList.OUR_PARTNERS,
        TabList.BECOME_PARTNER_REQUESTS
    )

    override fun createFragment(position: Int): Fragment {
        return when (tabs[position]) {
            TabList.OUR_PARTNERS -> OurPartnersTabFragment()
            TabList.BECOME_PARTNER_REQUESTS -> UserRequestsTabFragment()
        }
    }

    override fun getItemCount(): Int = tabs.size

    fun getTabByPosition(position: Int) = tabs[position]

    fun getPositionByTab(tab: TabList) = tabs.indexOf(tab)
}