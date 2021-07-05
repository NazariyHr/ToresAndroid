package com.devcraft.tores.presentation.ui.main.more.partners.becomePartner

import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.more.partners.PartnersViewModel
import com.devcraft.tores.utils.extensions.nullIfEmpty
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.functions.checkAllIsNotEmptyAndSetError
import com.devcraft.tores.utils.functions.setClearErrorAfterTextChanged
import com.devcraft.tores.utils.functions.watchAllEditTextFilled
import kotlinx.android.synthetic.main.fragment_become_partner.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BecomePartnerFragment : BaseFragment(R.layout.fragment_become_partner),
    CopyButtonUrlDialog.Callback {

    override val vm: BecomePartnerViewModel by viewModel()
    private val vmPartners: PartnersViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.become_partner))
        showBaseActivityBackButton()
        attachKeyboardListener()
    }

    override fun onDetach() {
        super.onDetach()
        hideKeyboard()
        detachKeyboardListener()
        getBaseActivity()?.showBottomBar()
    }

    override fun initListeners() {
        super.initListeners()

        setClearErrorAfterTextChanged(
            mapOf(
                tietCompanyName to tilCompanyName,
                tietLink to tilLink,
                tietContacts to tilContacts
            )
        )
        watchAllEditTextFilled(mutableListOf(tietCompanyName, tietLink, tietContacts)) {
            btnSend.isEnabled = it
        }
        btnSend.setSafeOnClickListener {
            val allDataIsNotEntered = checkAllIsNotEmptyAndSetError(
                mapOf(
                    tietCompanyName to tilCompanyName,
                    tietLink to tilLink,
                    tietContacts to tilContacts
                ), getString(R.string.fill_the_field)
            )
            if (allDataIsNotEntered) {
                return@setSafeOnClickListener
            }

            val companyName = tietCompanyName.text.toString()
            val url = tietLink.text.toString()
            val contacts = tietContacts.text.toString()
            val percent = tietPercent.text.toString()
            val type = tietType.text.toString()
            val additional = tietAdditionalInfo.text.toString()

            vm.sendBecomePartnerRequest(
                companyName,
                url,
                contacts,
                percent.nullIfEmpty(),
                type.nullIfEmpty(),
                additional.nullIfEmpty()
            )
        }
    }

    override fun initObservers() {
        super.initObservers()
        observeProcessAliveWithOverlay(progress_overlay)
        observeFailure(vm.onBecomePartnerFailure)
        observeSuccess(vm.onBecomePartnerSuccess) {
            vmPartners.refreshData()
            openDialog(CopyButtonUrlDialog.newInstance(this))
        }
    }

    override fun onCloseDialog() {
        onBackPressed()
    }
}
