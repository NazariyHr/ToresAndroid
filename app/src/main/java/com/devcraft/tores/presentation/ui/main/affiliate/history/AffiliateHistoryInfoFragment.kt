package com.devcraft.tores.presentation.ui.main.affiliate.history

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.entities.AffiliateTreeUser
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_affiliate_history.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AffiliateHistoryInfoFragment : BaseFragment(R.layout.fragment_affiliate_history),
    SelectedTreeUsersHistoryAdapter.Callback,
    LineUsersAdapter.Callback {
    override val vm: AffiliateHistoryViewModel by sharedViewModel()

    private val adapterSelectedUsersHistory = SelectedTreeUsersHistoryAdapter()
    private val adapterUsersLine = LineUsersAdapter()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.watch_profit_from_tree_title))
        showBaseActivityBackButton()

        rvSelectedTreeUsersHistory.adapter = adapterSelectedUsersHistory
        rvSelectedTreeUsersHistory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        rvUsersLine.adapter = adapterUsersLine
        rvUsersLine.layoutManager = LinearLayoutManager(context)

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()

        adapterSelectedUsersHistory.callback = this
        adapterUsersLine.callback = this

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
            rvUsersLine.setVisible(!it)
            pbUsersLine.setVisible(it)
            if (it) {
                tvListAreEmpty.setGone()
            }
        })

        vm.allProcessesAreFailed.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible(it)
        })

        vm.usersInLine.observe(viewLifecycleOwner, {
            rvUsersLine.setVisible(it != null)
            if (it != null) {
                adapterUsersLine.items = it
                tvListAreEmpty.setVisible(it.isEmpty())
            }
        })
        vm.selectedTreeUsersHistory.observe(viewLifecycleOwner, {
            adapterSelectedUsersHistory.items = it
            rvSelectedTreeUsersHistory.smoothScrollToPosition(adapterSelectedUsersHistory.itemCount - 1)
        })
        vm.onGetAffiliateTreeUsersFailure.observe(viewLifecycleOwner, {
            showToast(it.message.orEmpty())
        })
    }

    override fun onFirstLineClicked() {
        vm.onFirstLineClicked()
    }

    override fun onUserClick(user: AffiliateTreeUser) {
        vm.onUserClickFromHistory(user)
    }

    override fun onUserInLineClick(user: AffiliateTreeUser) {
        vm.onClickUserInLine(user)
    }
}
