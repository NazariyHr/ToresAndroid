package com.devcraft.tores.presentation.ui.main.more.ranks.rankSystem

import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ranks_system.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RanksSystemFragment : BaseFragment(R.layout.fragment_ranks_system) {
    override val vm: RanksSystemViewModel by viewModel()
    private val adapter: RankSystemLevelAdapter = RankSystemLevelAdapter()

    override fun initViews() {
        setBaseActivityToolbarTitle(getString(R.string.rank_system))
        showBaseActivityBackButton()

        rvRanksSystemLevels.adapter = adapter
        rvRanksSystemLevels.layoutManager = LinearLayoutManager(requireContext())
        vm.loadData()
    }

    override fun initObservers() {
        super.initObservers()
        vm.rankSystemLevels.observe(viewLifecycleOwner, {
            adapter.items = it
        })
    }
}