package com.devcraft.tores.presentation.ui.main.more.partners.ourPartners

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.more.partners.PartnersViewModel
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tab_our_partners.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OurPartnersTabFragment : BaseFragment(R.layout.fragment_tab_our_partners),
    PartnersAdapter.Callback {

    override val vm: PartnersViewModel by sharedViewModel()
    private val adapter = PartnersAdapter()

    override fun initViews() {
        super.initViews()
        rvPartners.adapter = adapter
        rvPartners.layoutManager = LinearLayoutManager(context)
    }

    override fun initListeners() {
        super.initListeners()
        adapter.callback = this
    }

    override fun initObservers() {
        super.initObservers()
        vm.dataLoadingFailed.observe(viewLifecycleOwner, {
            rvPartners.setVisible(!it)
            tvLoadingDataError.setVisible(it)
        })
        vm.partners.observe(viewLifecycleOwner, {
            adapter.items = it
        })
    }

    override fun onUrlClicked(url: String) {
        openBrowser(url)
    }
}
