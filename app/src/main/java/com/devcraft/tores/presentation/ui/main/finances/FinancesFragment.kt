package com.devcraft.tores.presentation.ui.main.finances

import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_finances.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FinancesFragment : BaseFragment(R.layout.fragment_finances), TabTitlesAdapter.Callback {

    companion object {
        const val ARG_OPEN_FRAGMENT = "arg_open_fragment"
    }

    override val vm: FinancesViewModel by sharedViewModel()

    private val adapterTitles: TabTitlesAdapter = TabTitlesAdapter()
    private lateinit var pagerAdapter: TabsPagerAdapter

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.finances))
        hideBaseActivityBackButton()

        rvTabTitles.adapter = adapterTitles
        rvTabTitles.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        pagerAdapter = TabsPagerAdapter(requireActivity())
        vpTabs.adapter = pagerAdapter
    }

    override fun parseArguments(arguments: Bundle) {
        arguments.let {
            if (it.containsKey(ARG_OPEN_FRAGMENT)) {
                val tabToOpen = TabList.valueOf(it.getString(ARG_OPEN_FRAGMENT)!!)
                setDefaultTab(tabToOpen)
            }
        }
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
        val p = pagerAdapter.getPositionByTab(tab)
        vpTabs.currentItem = p
        vpTabs.isUserInputEnabled = true
    }

    private fun setDefaultTab(tab: TabList) {
        val p = pagerAdapter.getPositionByTab(tab)
        vpTabs.post {
            vpTabs.setCurrentItem(p, false)
        }
    }
}
