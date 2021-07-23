package com.devcraft.tores.presentation.ui.main.shares.sharesHistory

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_shares_history.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SharesHistoryFragment : BaseFragment(R.layout.fragment_shares_history) {

    override val vm: SharesHistoryViewModel by viewModel()

    private val adapter: SharesHistoryAdapter = SharesHistoryAdapter()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.shares_history))
        showBaseActivityBackButton()

        rvSharesHistory.adapter = adapter
        rvSharesHistory.layoutManager = LinearLayoutManager(context)

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()
        swipeRefresh.setOnRefreshListener {
            vm.refreshData()
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

        vm.history.observe(viewLifecycleOwner, {
            tvLoadingDataError.setGone()
            rvSharesHistory.setVisible()
            tvDataEmpty.setVisible(it.isEmpty())
            adapter.items = it
        })

        vm.onGetHistoryFailure.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible()
            rvSharesHistory.setGone()
            showToast(it.message.orEmpty())
        })
    }
}
