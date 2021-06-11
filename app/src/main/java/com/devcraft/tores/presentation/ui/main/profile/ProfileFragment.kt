package com.devcraft.tores.presentation.ui.main.profile

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.devcraft.tores.R
import com.devcraft.tores.entities.Rank
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.auth.AuthActivity
import com.devcraft.tores.presentation.ui.main.profile.changePassword.ChangePasswordFragment
import com.devcraft.tores.presentation.ui.main.profile.financePassword.FinancePasswordFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val vm: ProfileViewModel by sharedViewModel()


    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.profile))
        showBaseActivityBackButton()

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()

        swipeRefresh.setOnRefreshListener {
            vm.refreshData()
        }
        llReferralLink.setSafeOnClickListener {
            val clipboard: ClipboardManager? =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText(
                getString(R.string.referral_link),
                tvReferralLink.text.toString()
            )
            clipboard?.setPrimaryClip(clip)

            showToast(getString(R.string.link_copied))
        }
        cvFinancePassword.setSafeOnClickListener {
            openFragment(R.id.container, FinancePasswordFragment())
        }
        btnChangePassword.setSafeOnClickListener {
            openFragment(R.id.container, ChangePasswordFragment())
        }
        btnLogOut.setSafeOnClickListener {
            vm.clearAuthToken()
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
            btnLogOut.setVisible(!it)
        })
        vm.onGetUserFailure.observe(viewLifecycleOwner, {
            cvMainInfo.setGone()
            cvReferralInfo.setGone()
            cvFinancePassword.setGone()
            btnChangePassword.setGone()
            tvLoadingDataError.setVisible()

            showToast(it)
        })
        vm.userInfo.observe(viewLifecycleOwner, {
            cvMainInfo.setVisible()
            cvReferralInfo.setVisible()
            cvFinancePassword.setVisible()
            btnChangePassword.setVisible()
            tvLoadingDataError.setGone()

            tvLogin.text = it.login

            it.currentRank.getRankDrawable(requireContext()).let { rankIcon ->
                ivRankBadge.setImageDrawable(rankIcon)
                ivRankBadge.setVisible(rankIcon != null)
            }
            tvRankTitle.text = it.currentRank.getRankText(requireContext())

            llNextRank.setVisible(it.currentRank != Rank.PRESIDENT)
            if (it.currentRank != Rank.PRESIDENT) {
                it.nextRank?.getRankDrawable(requireContext()).let { rankIcon ->
                    ivNextRankBadge.setImageDrawable(rankIcon)
                    ivNextRankBadge.setVisible(rankIcon != null)
                }
                tvNextRankTitle.text = it.nextRank?.getRankText(requireContext()).orEmpty()
            }

            tvEmail.text = it.email

            tvRegisteredAt.text = it.registeredAt
            tvLastEntrance.text = it.lastEntrance
            tvIp.text = it.ip

            tvReferralLink.text = "app.toresbusiness.com/r?r=" + it.referralCode

            tvFinancePasswordIsSet.setVisible(it.financePasswordSet)
            tvFinancePasswordIsNotSet.setVisible(!it.financePasswordSet)
        })
        vm.onAuthTokenCleared.observe(viewLifecycleOwner, {
            if (it) {
                openActivity(AuthActivity::class.java)
            }
        })
    }
}
