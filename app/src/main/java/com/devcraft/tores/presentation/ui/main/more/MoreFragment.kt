package com.devcraft.tores.presentation.ui.main.more

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.MainViewModel
import com.devcraft.tores.presentation.ui.main.more.affiliate.AffiliateFragment
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_more.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoreFragment : BaseFragment(R.layout.fragment_more) {

    override val vm: MainViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.more))
        hideBaseActivityBackButton()
    }

    override fun initListeners() {
        super.initListeners()

        llPartnerProgram.setSafeOnClickListener {
            openFragment(R.id.container, AffiliateFragment())
        }
    }
}