package com.devcraft.tores.presentation.ui.main.finances.rankProfits

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.entities.Rank
import com.devcraft.tores.entities.RankProfitsHistoryData
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tab_rank_profits.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RankProfitsTabFragment : BaseFragment(R.layout.fragment_tab_rank_profits) {

    override val vm: RankProfitsViewModel by sharedViewModel()

    private val adapter: RankProfitsHistoryAdapter = RankProfitsHistoryAdapter()

    override fun initViews() {
        super.initViews()

        rvRankProfitHistory.adapter = adapter
        rvRankProfitHistory.layoutManager = LinearLayoutManager(context)

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

        vm.data.observe(viewLifecycleOwner, {
            tvLoadingDataError.setGone()
            rvRankProfitHistory.setVisible()


            if (it.rankProfits.isEmpty()) {
                adapter.items = getStubData().rankProfits.toMutableList()
            } else {
                adapter.items = it.rankProfits.toMutableList()
            }
        })

        vm.onGetDataFailure.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible()
            rvRankProfitHistory.setGone()

            showToast(it.message.orEmpty())
        })
    }

    private fun getStubData(): RankProfitsHistoryData {
        return RankProfitsHistoryData(
            mutableListOf(
                RankProfitsHistoryData.RankProfit(
                    1,
                    "26.04.2021 12:00",
                    Rank.CLIENT,
                    1.0
                ),
                RankProfitsHistoryData.RankProfit(
                    2,
                    "26.04.2021 12:00",
                    Rank.AGENT,
                    10.0
                ),
                RankProfitsHistoryData.RankProfit(
                    3,
                    "26.04.2021 12:00",
                    Rank.MANAGER,
                    20.0
                ),
                RankProfitsHistoryData.RankProfit(
                    4,
                    "26.04.2021 12:00",
                    Rank.VIP_MANAGER,
                    50.0
                )
            )
        )
    }
}
