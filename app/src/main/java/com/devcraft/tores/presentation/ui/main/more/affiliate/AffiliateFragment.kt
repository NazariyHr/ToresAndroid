package com.devcraft.tores.presentation.ui.main.more.affiliate

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.more.affiliate.history.AffiliateHistoryInfoFragment
import com.devcraft.tores.presentation.ui.main.more.affiliate.referralProgramInfo.ReferralProgramInfoFragment
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_affiliate.btnGoToTree
import kotlinx.android.synthetic.main.fragment_affiliate.cvPartnersTotal
import kotlinx.android.synthetic.main.fragment_affiliate.cvRankInfo
import kotlinx.android.synthetic.main.fragment_affiliate.cvToresTurnover
import kotlinx.android.synthetic.main.fragment_affiliate.cvTotalProfit
import kotlinx.android.synthetic.main.fragment_affiliate.cvYourSponsor
import kotlinx.android.synthetic.main.fragment_affiliate.flRankInfo
import kotlinx.android.synthetic.main.fragment_affiliate.ivCurrentRank
import kotlinx.android.synthetic.main.fragment_affiliate.swipeRefresh
import kotlinx.android.synthetic.main.fragment_affiliate.tvCurrentLevel
import kotlinx.android.synthetic.main.fragment_affiliate.tvCurrentRank
import kotlinx.android.synthetic.main.fragment_affiliate.tvLinkClicks
import kotlinx.android.synthetic.main.fragment_affiliate.tvLoadingDataError
import kotlinx.android.synthetic.main.fragment_affiliate.tvPartnersActiveAmount
import kotlinx.android.synthetic.main.fragment_affiliate.tvPartnersNotActiveAmount
import kotlinx.android.synthetic.main.fragment_affiliate.tvPartnersTotalAmount
import kotlinx.android.synthetic.main.fragment_affiliate.tvProfit24Hours
import kotlinx.android.synthetic.main.fragment_affiliate.tvSendToMining
import kotlinx.android.synthetic.main.fragment_affiliate.tvSponsorEmail
import kotlinx.android.synthetic.main.fragment_affiliate.tvSponsorLogin
import kotlinx.android.synthetic.main.fragment_affiliate.tvSponsorPhone
import kotlinx.android.synthetic.main.fragment_affiliate.tvTotalProfit
import kotlinx.android.synthetic.main.fragment_affiliate.tvTotalToresSum
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AffiliateFragment : BaseFragment(R.layout.fragment_affiliate) {

    override val vm: AffiliateViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.partner_program))
        showBaseActivityBackButton()

        vm.loadData()
    }

    override fun initListeners() {
        super.initListeners()

        swipeRefresh.setOnRefreshListener {
            vm.refreshData()
        }
        flRankInfo.setSafeOnClickListener {
            openDialog(ReferralProgramInfoFragment())
        }
        tvSponsorEmail.setSafeOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text";
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(tvSponsorEmail.text.toString()))
            requireContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        }
        tvSponsorPhone.setSafeOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + tvSponsorPhone.text.toString())
            startActivity(dialIntent)
        }
        btnGoToTree.setSafeOnClickListener {
            openFragment(R.id.container, AffiliateHistoryInfoFragment())
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
        vm.allProcessesAreFailed.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible(it)
        })

        vm.affiliateInfo.observe(viewLifecycleOwner, {
            cvYourSponsor.setVisible()
            cvPartnersTotal.setVisible()
            cvToresTurnover.setVisible()
            cvTotalProfit.setVisible()
            btnGoToTree.setVisible()

            tvSponsorLogin.text = it.sponsorLogin
            tvSponsorEmail.text = it.sponsorEmail
            tvSponsorPhone.text = it.sponsorPhone

            tvPartnersTotalAmount.text = it.totalPartners.toString()
            tvPartnersActiveAmount.text = it.activePartners.toString()
            tvPartnersNotActiveAmount.text = it.inactivePartners.toString()

            tvSendToMining.text = it.activePartners.toString()
            tvLinkClicks.text = it.refLinkVisits.toString()
            tvTotalToresSum.text = String.format(
                resources.getString(R.string.tores_amount_int),
                it.totalDepositsAmount
            )

            tvTotalProfit.text = String.format(
                resources.getString(R.string.tores_amount),
                it.overallProfit
            )
            tvProfit24Hours.text = String.format(
                resources.getString(R.string.tores_amount),
                it.overallProfit24h
            )
        })
        vm.onGetAffiliateInfoFailure.observe(viewLifecycleOwner, {
            cvYourSponsor.setGone()
            cvPartnersTotal.setGone()
            cvToresTurnover.setGone()
            cvTotalProfit.setGone()
            btnGoToTree.setGone()

            showToast(it.message.orEmpty())
        })

        vm.rankInfo.observe(viewLifecycleOwner, {
            cvRankInfo.setVisible()

            it.currentRank.getRankDrawable(requireContext()).let { rankIcon ->
                ivCurrentRank.setImageDrawable(rankIcon)
                ivCurrentRank.setVisible(rankIcon != null)
            }
            tvCurrentRank.text = it.currentRank.getRankText(requireContext())
            tvCurrentLevel.text = "${it.myLevel}/6"
        })
        vm.onGetRankInfoFailure.observe(viewLifecycleOwner, {
            cvRankInfo.setGone()

            showToast(it.message.orEmpty())
        })
    }
}