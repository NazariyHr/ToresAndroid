package com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.TopupsAndWithdrawalsData
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesViewModel
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.transactionDetails.TransactionDetailsFragment
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.transactionDetails.TransactionDetailsViewModel
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tab_topups_and_withdrawals.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TopupsAndWithdrawalsTabFragment : BaseFragment(R.layout.fragment_tab_topups_and_withdrawals),
    TopupsAndWithdrawalsAdapter.Callback {

    override val vm: TopupsAndWithdrawalsViewModel by viewModel()
    private val vmFinances: FinancesViewModel by sharedViewModel()
    private val vmTransactionDetails: TransactionDetailsViewModel by sharedViewModel()

    private val adapter: TopupsAndWithdrawalsAdapter = TopupsAndWithdrawalsAdapter()

    override fun initViews() {
        super.initViews()

        rvTopupsAndWithdrawals.adapter = adapter
        rvTopupsAndWithdrawals.layoutManager = LinearLayoutManager(context)

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
            rvTopupsAndWithdrawals.setVisible()
            cvTotalTopups.setVisible()

            adapter.items = it.transactions.toMutableList()
            rvTopupsAndWithdrawals.setHasFixedSize(true)

            tvTotalTopups.text =
                String.format(resources.getString(R.string.tores_amount), it.totalTopUps)
        })

        vm.onGetDataFailure.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible()
            rvTopupsAndWithdrawals.setGone()
            cvTotalTopups.setGone()

            showToast(it.message.orEmpty())
        })

        vmFinances.needRefreshTopupsAndWithdrawals.observe(viewLifecycleOwner, {
            if (it) {
                vm.refreshData()
            }
        })
    }

    override fun onTransactionClicked(transaction: TopupsAndWithdrawalsData.Transaction) {
        vmTransactionDetails.selectedTransaction.postValue(transaction)
        openFragment(R.id.container, TransactionDetailsFragment())
    }
}