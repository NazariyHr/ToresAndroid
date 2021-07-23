package com.devcraft.tores.presentation.ui.main.finances.mining

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.entities.MiningHistoryData
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesViewModel
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tab_minings.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MiningsTabFragment : BaseFragment(R.layout.fragment_tab_minings),
    MiningHistoryAdapter.Callback {

    override val vm: MiningViewModel by viewModel()
    private val vmFinances: FinancesViewModel by sharedViewModel()

    private val adapter: MiningHistoryAdapter = MiningHistoryAdapter()

    override fun initViews() {
        super.initViews()

        rvMiningHistory.adapter = adapter
        rvMiningHistory.layoutManager = LinearLayoutManager(context)

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()

        adapter.callback = this

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
            rvMiningHistory.setVisible()
            cvTotalInMining.setVisible()

            tvDataEmpty.setVisible(it.transactions.isEmpty())

            adapter.items = it.transactions.toMutableList()

            tvTotalInMining.text =
                String.format(resources.getString(R.string.tores_amount), it.myDeposit)
        })

        vm.onGetDataFailure.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible()
            rvMiningHistory.setGone()
            cvTotalInMining.setGone()

            showToast(it.message.orEmpty())
        })

        vm.onCancelWithdrawSuccess.observe(viewLifecycleOwner, {
            showToast(it)
            vm.refreshData()
            vmFinances.needRefreshTopupsAndWithdrawals.postValue(true)
        })

        vm.onCancelWithdrawFailure.observe(viewLifecycleOwner, {
            showToast(it.message.orEmpty())
        })
    }

    override fun onCancelWithdrawFromDepositClicked(transaction: MiningHistoryData.Transaction) {
        vm.cancelWithdraw(transaction.id)
    }
}