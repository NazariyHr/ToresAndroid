package com.devcraft.tores.presentation.ui.main.finances

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_finances.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FinancesFragment : BaseFragment(R.layout.fragment_finances), TabTitlesAdapter.Callback {

    override val vm: FinancesViewModel by sharedViewModel()

    private val adapterTitles: TabTitlesAdapter = TabTitlesAdapter()
    private lateinit var pagerAdapter: TabsPagerAdapter

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.finances))
        rvTabTitles.adapter = adapterTitles
        rvTabTitles.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        pagerAdapter = TabsPagerAdapter(requireActivity())
        vpTabs.adapter = pagerAdapter
    }

    override fun initListeners() {
        super.initListeners()
        adapterTitles.callback = this
        vpTabs.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                adapterTitles.setSelectedTab(pagerAdapter.getTabByPosition(position))
                rvTabTitles.scrollToPosition(position)
            }
        })
    }

    override fun onTabClicked(tab: TabList) {
        vpTabs.currentItem = pagerAdapter.getPositionByTab(tab)
        vpTabs.isUserInputEnabled = true
    }
}