package com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_tab_topups_and_withdrawals.*
import kotlinx.android.synthetic.main.fragment_tab_topups_and_withdrawals.swipeRefresh
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TopupsAndWithdrawalsTabFragment : BaseFragment(R.layout.fragment_tab_topups_and_withdrawals) {

    override val vm: TopupsAndWithdrawalsViewModel by sharedViewModel()

    private val adapter: TopupsAndWithdrawalsAdapter = TopupsAndWithdrawalsAdapter()

    override fun initViews() {
        super.initViews()

        rvTopupsAndWithdrawals.adapter = adapter
        rvTopupsAndWithdrawals.layoutManager = LinearLayoutManager(context)
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

        vm.data.observe(viewLifecycleOwner, {
            tvLoadingDataError.setGone()
            rvTopupsAndWithdrawals.setVisible()
            cvTotalTopups.setVisible()

            adapter.items = it.transactions.toMutableList()

            tvTotalTopups.text =
                String.format(resources.getString(R.string.tores_amount), it.totalTopUps)
        })

        vm.onGetDataFailure.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible()
            rvTopupsAndWithdrawals.setGone()
            cvTotalTopups.setGone()

            showToast(it.message.orEmpty())
        })
    }
}