package com.devcraft.tores.presentation.ui.main.more.partners

import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_partners.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PartnersFragment : BaseFragment(R.layout.fragment_partners) {

    override val vm: PartnersViewModel by sharedViewModel()

    private lateinit var pagerAdapter: TabsPagerAdapter

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.partners))
        showBaseActivityBackButton()

        pagerAdapter = TabsPagerAdapter(requireActivity())
        vpTabs.adapter = pagerAdapter
        setSelectedTab(TabList.OUR_PARTNERS)

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()
        llTabOurPartners.setSafeOnClickListener {
            setSelectedTab(TabList.OUR_PARTNERS, true)
        }
        llTabMyRequests.setSafeOnClickListener {
            setSelectedTab(TabList.BECOME_PARTNER_REQUESTS, true)
        }
        swipeRefresh.setOnRefreshListener {
            vm.refreshData()
        }

        vpTabs.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setSelectedTab(pagerAdapter.getTabByPosition(position))
            }
        })
    }

    private fun setSelectedTab(tab: TabList, setInPager: Boolean = false) {
        when (tab) {
            TabList.OUR_PARTNERS -> {
                llTabOurPartners.isSelected = true
                llTabMyRequests.isSelected = false

                tvTitleOurPartners.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_primary_color
                    )
                )
                tvTitleMyRequests.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.text_color
                    )
                )
            }
            TabList.BECOME_PARTNER_REQUESTS -> {
                llTabOurPartners.isSelected = false
                llTabMyRequests.isSelected = true

                tvTitleMyRequests.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_primary_color
                    )
                )
                tvTitleOurPartners.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.text_color
                    )
                )
            }
        }
        if (setInPager) {
            val p = pagerAdapter.getPositionByTab(tab)
            vpTabs.currentItem = p
            vpTabs.isUserInputEnabled = true
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
        observeFailure(vm.onGetInfoFailure)
    }
}
