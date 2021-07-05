package com.devcraft.tores.presentation.ui.main.more.partners.userRequests

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.more.partners.PartnersViewModel
import com.devcraft.tores.presentation.ui.main.more.partners.becomePartner.BecomePartnerFragment
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tab_become_partner_requests.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserRequestsTabFragment : BaseFragment(R.layout.fragment_tab_become_partner_requests),
    BecomePartnerRequestsAdapter.Callback {

    override val vm: PartnersViewModel by sharedViewModel()
    private val adapter = BecomePartnerRequestsAdapter()

    override fun initViews() {
        super.initViews()
        rvRequests.adapter = adapter
        rvRequests.layoutManager = LinearLayoutManager(context)
    }

    override fun initListeners() {
        super.initListeners()
        adapter.callback = this
        ivAdd.setSafeOnClickListener {
            openFragment(R.id.container, BecomePartnerFragment())
        }
        llButtonHtmlCode.setSafeOnClickListener {
            val clipboard: ClipboardManager? =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText(
                getString(R.string.our_button_for_your_site),
                tvButtonHtmlCode.text.toString()
            )
            clipboard?.setPrimaryClip(clip)
            showToast(getString(R.string.link_copied))
        }
    }

    override fun initObservers() {
        super.initObservers()
        vm.dataLoadingFailed.observe(viewLifecycleOwner, {
            rvRequests.setVisible(!it)
            ivAdd.setVisible(!it)
            tvButtonHtmlCodeTitle.setVisible(!it)
            llButtonHtmlCode.setVisible(!it)
            tvLoadingDataError.setVisible(it)
        })
        vm.becomePartnerRequests.observe(viewLifecycleOwner, {
            adapter.items = it
            tvButtonHtmlCodeTitle.setVisible(it.isNotEmpty())
            llButtonHtmlCode.setVisible(it.isNotEmpty())
        })
    }

    override fun onUrlClicked(url: String) {
        openBrowser(url)
    }
}
