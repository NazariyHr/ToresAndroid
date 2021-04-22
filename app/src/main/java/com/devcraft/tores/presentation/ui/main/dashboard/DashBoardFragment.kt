package com.devcraft.tores.presentation.ui.main.dashboard

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashBoardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val vm: DashBoardViewModel by sharedViewModel()

    private val profitsAdapter: ProfitsAdapter = ProfitsAdapter()
    private val registersAdapter: RegistersAdapter = RegistersAdapter()

    lateinit var selectedDrawable: Drawable

    override fun initViews() {
        selectedDrawable = btnByPartners.background
        btnByPartners.setOnClickListener {
            btnByPartners.background = selectedDrawable
            btnByPartners.setTextColor(requireContext().resources.getColor(R.color.black))
            btnByRanks.background = null
            btnByRanks.setTextColor(requireContext().resources.getColor(R.color.white))
            tvRewardsPartners.setVisible()
            tvRewardsRanks.setGone()
        }
        btnByRanks.setOnClickListener {
            btnByRanks.background = selectedDrawable
            btnByRanks.setTextColor(requireContext().resources.getColor(R.color.black))
            btnByPartners.background = null
            btnByPartners.setTextColor(requireContext().resources.getColor(R.color.white))
            tvRewardsRanks.setVisible()
            tvRewardsPartners.setGone()
        }

        rvProfits.adapter = profitsAdapter
        rvProfits.layoutManager = LinearLayoutManager(context)

        rvRegistrations.adapter = registersAdapter
        rvRegistrations.layoutManager = LinearLayoutManager(context)
    }

    override fun initObservers() {
        super.initObservers()

        vm.userInfo.observe(viewLifecycleOwner, {
            cvBalance.setVisible()
            cvBonusBalance.setVisible()
            cvIncome.setVisible()
            cvRewards.setVisible()

            tvBalance.text = String.format(resources.getString(R.string.tores_amount), it.balance)
            tvBonusBalance.text =
                String.format(resources.getString(R.string.tores_amount), it.rankBalance)

            tvIncome.text =
                String.format(resources.getString(R.string.tores_amount), it.totalProfit)
            tvRewardsPartners.text =
                String.format(resources.getString(R.string.tores_amount), it.partnerProfit)
            tvRewardsRanks.text =
                String.format(resources.getString(R.string.tores_amount), it.rankProfit)
        })
        vm.onGetUserFailure.observe(viewLifecycleOwner, {
            cvBalance.setGone()
            cvBonusBalance.setGone()
            cvIncome.setGone()
            cvRewards.setGone()

            showToast(it.message.orEmpty())
        })
        vm.profitsAndRegisters.observe(viewLifecycleOwner, {
            cvFiveLastTransactions.setVisible()
            profitsAdapter.items = it.profits
            registersAdapter.items = it.registers
        })

        vm.onGetProfitsAndRegistersFailure.observe(viewLifecycleOwner, {
            cvFiveLastTransactions.setGone()
            showToast(it.message.orEmpty())
        })
    }
}
