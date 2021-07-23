package com.devcraft.tores.presentation.ui.main.shares.transferShares

import android.annotation.SuppressLint
import androidx.core.widget.doAfterTextChanged
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.shares.SharesViewModel
import com.devcraft.tores.utils.extensions.combineWith
import com.devcraft.tores.utils.extensions.intValue
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import com.devcraft.tores.utils.inputFilters.InputFilterMinMaxInt
import kotlinx.android.synthetic.main.fragment_transfer_shares.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransferSharesFragment : BaseFragment(R.layout.fragment_transfer_shares) {

    override val vm: TransferSharesViewModel by viewModel()
    private val vmShares: SharesViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.do_transfer_shares_to_user_title))
        showBaseActivityBackButton()
        attachKeyboardListener()
    }

    override fun onDetach() {
        super.onDetach()
        hideKeyboard()
        detachKeyboardListener()
        getBaseActivity()?.showBottomBar()
    }

    @SuppressLint("SetTextI18n")
    override fun initListeners() {
        super.initListeners()
        swipeRefresh.setOnRefreshListener {
            vmShares.refreshData()
        }
        tietLogin.doAfterTextChanged {
            btnSend.isEnabled = !it.isNullOrEmpty() && !tietTransferSum.text.isNullOrEmpty()
        }
        tietTransferSum.doAfterTextChanged {
            btnSend.isEnabled = !it.isNullOrEmpty() && !tietLogin.text.isNullOrEmpty()
        }
        btnSend.setSafeOnClickListener {
            vm.transferShares(tietTransferSum.intValue(), tietLogin.text.toString())
        }
    }

    override fun initObservers() {
        super.initObservers()
        vmShares.someProcessAlive.combineWith(vm.someProcessAlive) { v1, v2 ->
            v1 == true || v2 == true
        }.observe(viewLifecycleOwner, {
            if (swipeRefresh.isRefreshing && !it) {
                swipeRefresh.isRefreshing = it
            }
            progress_overlay.setVisible(it)
        })
        vmShares.sharesTotalInfo.observe(viewLifecycleOwner, {
            tvSharesBalance.text = getString(R.string.shares_amount_str, it.myBalance)
            tietTransferSum.filters =
                arrayOf(InputFilterMinMaxInt(0, it.myBalance.toDouble().toInt()))
        })
        vmShares.allProcessesAreSuccess.observe(viewLifecycleOwner, {
            llContent.setVisible(it)
        })
        vmShares.allProcessesAreFailed.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible(it)
        })
        vm.onTransferToUserSuccess.observe(viewLifecycleOwner, {
            showToast(getString(R.string.shares_successfully_bought))
            vmShares.refreshData()
            onBackPressed()
        })
        vm.onTransferToUserFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }
}
