package com.devcraft.tores.presentation.ui.main.affiliate.referralProgramInfo

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseDialogFragment
import com.devcraft.tores.presentation.ui.main.affiliate.AffiliateViewModel
import com.devcraft.tores.utils.MetricsUtils
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.dialog_affiliate_referral_program_info.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ReferralProgramInfoFragment :
    BaseDialogFragment(R.layout.dialog_affiliate_referral_program_info) {
    override val vm: AffiliateViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()

        val maxH = MetricsUtils.dpToPx(requireContext(), 400)
        maxH.let {
            root.post {
                val lp = root.layoutParams
                val height = root.measuredHeight
                if (height > maxH) {
                    lp.height = maxH
                    root.layoutParams = lp
                }
            }
        }
    }

    override fun initListeners() {
        super.initListeners()

        flClose.setSafeOnClickListener {
            dismiss()
        }
    }
}