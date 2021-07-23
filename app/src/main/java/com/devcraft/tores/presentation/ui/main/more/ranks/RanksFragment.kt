package com.devcraft.tores.presentation.ui.main.more.ranks

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.more.ranks.about.AboutRanksFragment
import com.devcraft.tores.presentation.ui.main.more.ranks.rankSystem.RanksSystemFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_ranks.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RanksFragment : BaseFragment(R.layout.fragment_ranks) {

    override val vm: RanksViewModel by viewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.ranks))
        showBaseActivityBackButton()

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()

        swipeRefresh.setOnRefreshListener {
            vm.refreshData()
        }
        btnAboutRanks.setSafeOnClickListener {
            openFragment(R.id.container, AboutRanksFragment())
        }
        btnRankSystem.setSafeOnClickListener {
            openFragment(R.id.container, RanksSystemFragment())
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initObservers() {
        super.initObservers()

        vm.someProcessAlive.observe(viewLifecycleOwner, {
            if (swipeRefresh.isRefreshing && !it) {
                swipeRefresh.isRefreshing = it
            }
            progress_overlay.setVisible(it)
        })
        vm.allProcessesAreSuccess.observe(viewLifecycleOwner, {
            if (it) {
                cvContent.setVisible()
            }
        })
        vm.allProcessesAreFailed.observe(viewLifecycleOwner, {
            if (it) {
                tvLoadingDataError.setVisible()
                cvContent.setGone()
            }
        })
        vm.onGeUserRankInfoFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
        vm.onGetUserRankSystemInfoFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })

        vm.userRankInfo.observe(viewLifecycleOwner, {
            it.currentRank.getRankDrawable(requireContext()).let { rankIcon ->
                ivRankBadge.setImageDrawable(rankIcon)
                ivRankBadge.setVisible(rankIcon != null)
            }
            tvRankTitle.text = it.currentRank.getRankText(requireContext())

            it.nextRank.getRankDrawable(requireContext()).let { rankIcon ->
                ivNextRankBadge.setImageDrawable(rankIcon)
                ivNextRankBadge.setVisible(rankIcon != null)
            }
            tvNextRankTitle.text = it.nextRank.getRankText(requireContext())
        })
        vm.userRankSystemInfo.observe(viewLifecycleOwner, {
            tvBalance.text = getString(R.string.tores_amount_str, it.myDeposit)

            val myLevel = it.myLevel
            val txt = getString(R.string.your_referral_level, myLevel)
            val spannable = SpannableStringBuilder(txt)
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                txt.indexOf(myLevel.toString()),
                txt.indexOf(myLevel.toString()) + myLevel.toString().length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvYourReferralLevel.text = spannable

            tvMarketingBaseTores.text =
                getString(R.string.marketing_base_tores, it.volume, it.nextVolume)
        })
    }
}
