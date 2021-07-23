package com.devcraft.tores.presentation.ui.main.finances.transfers

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.entities.TransfersHistoryData
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesViewModel
import com.devcraft.tores.presentation.ui.main.finances.transfers.transferDetails.TransferDetailsFragment
import com.devcraft.tores.presentation.ui.main.finances.transfers.transferDetails.TransferDetailsViewModel
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tab_transfers.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransfersTabFragment : BaseFragment(R.layout.fragment_tab_transfers),
    TransfersHistoryAdapter.Callback {

    override val vm: TransfersViewModel by viewModel()
    private val vmFinances: FinancesViewModel by sharedViewModel()
    private val vmTransferDetails: TransferDetailsViewModel by sharedViewModel()


    private val adapter: TransfersHistoryAdapter = TransfersHistoryAdapter()

    override fun initViews() {
        super.initViews()

        rvTransfersHistory.adapter = adapter
        rvTransfersHistory.layoutManager = LinearLayoutManager(context)

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
            rvTransfersHistory.setVisible()

            tvDataEmpty.setVisible(it.transactions.isEmpty())

            adapter.items = it.transactions.toMutableList()
        })

        vm.onGetDataFailure.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible()
            rvTransfersHistory.setGone()

            showToast(it.message.orEmpty())
        })

        vmFinances.needRefreshTransfers.observe(viewLifecycleOwner, {
            if (it) {
                vm.refreshData()
            }
        })
    }

    override fun onEnterTheCodeClicked(transaction: TransfersHistoryData.Transaction) {
        vmTransferDetails.selectedTransfer.postValue(transaction)
        openFragment(R.id.container, TransferDetailsFragment())
    }
}
