package com.devcraft.tores.presentation.ui.main.finances.partnersProfits

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.entities.ReferralProfitsHistoryData
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tab_partners_rewards.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PartnersProfitsTabFragment : BaseFragment(R.layout.fragment_tab_partners_rewards) {


    override val vm: PartnersProfitsViewModel by sharedViewModel()

    private val adapter: ReferralProfitsHistoryAdapter = ReferralProfitsHistoryAdapter()

    override fun initViews() {
        super.initViews()

        rvReferralProfitHistory.adapter = adapter
        rvReferralProfitHistory.layoutManager = LinearLayoutManager(context)
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
            rvReferralProfitHistory.setVisible()
            cvTotalProfit.setVisible()


            if (it.referralProfits.isEmpty()) {
                val stubData = getStubData()
                adapter.items = stubData.referralProfits.toMutableList()

                tvTotalProfit.text =
                    String.format(resources.getString(R.string.tores_amount), stubData.total)
            } else {
                adapter.items = it.referralProfits.toMutableList()

                tvTotalProfit.text =
                    String.format(resources.getString(R.string.tores_amount), it.total)
            }
        })

        vm.onGetDataFailure.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible()
            rvReferralProfitHistory.setGone()
            cvTotalProfit.setGone()

            showToast(it.message.orEmpty())
        })
    }

    private fun getStubData(): ReferralProfitsHistoryData {
        return ReferralProfitsHistoryData(
            mutableListOf(
                ReferralProfitsHistoryData.ReferralProfit(
                    1,
                    "26.04.2021 11:53",
                    "some login1",
                    "1",
                    "48",
                    "10",
                    4.8
                ),
                ReferralProfitsHistoryData.ReferralProfit(
                    1,
                    "27.04.2021 12:33",
                    "some login2",
                    "2",
                    "80",
                    "5",
                    4.0
                )
            ),
            8.8
        )
    }
}
