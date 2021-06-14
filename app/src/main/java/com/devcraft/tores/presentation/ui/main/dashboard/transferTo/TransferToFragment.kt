package com.devcraft.tores.presentation.ui.main.dashboard.transferTo

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.AdapterView
import androidx.core.widget.doAfterTextChanged
import com.devcraft.tores.R
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.common.adapter.balanceAdapter.BalanceListAdapter
import com.devcraft.tores.presentation.common.adapter.balanceAdapter.DH
import com.devcraft.tores.utils.extensions.doubleValue
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import com.devcraft.tores.utils.inputFilters.InputFilterMinMaxDec
import kotlinx.android.synthetic.main.fragment_transfer_to.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransferToFragment : BaseFragment(R.layout.fragment_transfer_to) {

    override val vm: TransferToViewModel by viewModel()
    private lateinit var selectedDrawable: Drawable
    private lateinit var adapter: BalanceListAdapter

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.send_tokens))
        showBaseActivityBackButton()
        attachKeyboardListener()
        selectedDrawable = btnTabTransferToUser.background
        llTransferToUser.setVisible()
        vm.loadData()
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
            vm.refreshData()
        }
        btnTabTransferToUser.setOnClickListener {
            btnTabTransferToUser.background = selectedDrawable
            btnTabTransferToUser.setTextColor(requireContext().resources.getColor(R.color.black))
            btnTabTransferToExchange.background = null
            btnTabTransferToExchange.setTextColor(requireContext().resources.getColor(R.color.white))
            llTransferToUser.setVisible()
            llTransferToExchange.setGone()
        }
        btnTabTransferToExchange.setOnClickListener {
            btnTabTransferToExchange.background = selectedDrawable
            btnTabTransferToExchange.setTextColor(requireContext().resources.getColor(R.color.black))
            btnTabTransferToUser.background = null
            btnTabTransferToUser.setTextColor(requireContext().resources.getColor(R.color.white))
            llTransferToExchange.setVisible()
            llTransferToUser.setGone()
        }
        actvBalance.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selected = adapter.getItem(position)
                selected?.let {
                    onBalanceTypeSelected(it)
                }
            }
        tietLogin.doAfterTextChanged {
            btnTransferToUser.isEnabled =
                !it.isNullOrEmpty() && !tietToresTransferToUser.text.isNullOrEmpty()
        }
        tietToresTransferToUser.doAfterTextChanged {
            btnTransferToUser.isEnabled = !it.isNullOrEmpty() && !tietLogin.text.isNullOrEmpty()
        }
        flToresTransferToUserMax.setSafeOnClickListener {
            vm.miningInfo.value?.let { mInfo ->
                vm.selectedBalanceType?.let { bType ->
                    when (bType) {
                        BalanceType.BALANCE -> {
                            tietToresTransferToUser.setText(mInfo.myBalance.toString())
                        }
                        BalanceType.RANK_BALANCE -> {
                            tietToresTransferToUser.setText(mInfo.myRankBalance.toString())
                        }
                        BalanceType.NOT_SPECIFIED -> {
                            tietToresTransferToUser.setText(mInfo.myBalance.toString())
                        }
                    }
                }
            }
        }
        btnTransferToUser.setSafeOnClickListener {
            vm.transferToUser(tietToresTransferToUser.doubleValue(), tietLogin.text.toString())
        }
        tietToresTransferToExchange.doAfterTextChanged {
            btnTransferToExchange.isEnabled =
                !it.isNullOrEmpty() && !tietWallet.text.isNullOrEmpty()
        }
        tietWallet.doAfterTextChanged {
            btnTransferToExchange.isEnabled =
                !it.isNullOrEmpty() && !tietToresTransferToExchange.text.isNullOrEmpty()
        }
        flToresTransferToExchangeMax.setSafeOnClickListener {
            vm.miningInfo.value?.let { mInfo ->
                tietToresTransferToExchange.setText(mInfo.myBalance.toString())
            }
        }
        btnTransferToExchange.setSafeOnClickListener {
            vm.transferToExchange(
                tietToresTransferToExchange.doubleValue(),
                tietWallet.text.toString()
            )
        }
    }

    override fun initObservers() {
        super.initObservers()

        vm.someProcessAlive.observe(viewLifecycleOwner, {
            if (swipeRefresh.isRefreshing && !it) {
                swipeRefresh.isRefreshing = it
            }
            progress_overlay.setVisible(it)
        })
        vm.allProcessesAreSuccess.observe(viewLifecycleOwner, {
            llContent.setVisible(it)
        })
        vm.allProcessesAreFailed.observe(viewLifecycleOwner, {
            tvLoadingDataError.setVisible(it)
        })
        vm.miningInfo.observe(viewLifecycleOwner, { mInfo ->
            tietToresTransferToExchange.filters =
                arrayOf(InputFilterMinMaxDec(0.0, mInfo.myBalance.toDouble()))

            val items = mutableListOf(
                DH(
                    getString(R.string.main_balance),
                    BalanceType.BALANCE,
                    mInfo.myBalance.toString()
                ),
                DH(
                    getString(R.string.rank_balance),
                    BalanceType.RANK_BALANCE,
                    mInfo.myRankBalance.toString()
                )
            )
            adapter = BalanceListAdapter(requireContext(), items)
            actvBalance?.setAdapter(adapter)

            if (actvBalance.text.toString().isEmpty()) {
                items.first().let {
                    actvBalance.setText(it.toString(), false)
                    onBalanceTypeSelected(it)
                }
            } else {
                vm.selectedBalanceType?.let {
                    val refreshedDh = when (it) {
                        BalanceType.BALANCE -> {
                            DH(
                                getString(R.string.main_balance),
                                BalanceType.BALANCE,
                                mInfo.myBalance.toString()
                            )
                        }
                        BalanceType.RANK_BALANCE -> {
                            DH(
                                getString(R.string.rank_balance),
                                BalanceType.RANK_BALANCE,
                                mInfo.myRankBalance.toString()
                            )
                        }
                        else -> {
                            DH(
                                getString(R.string.main_balance),
                                BalanceType.BALANCE,
                                "0"
                            )
                        }
                    }
                    tietToresTransferToUser.filters =
                        arrayOf(InputFilterMinMaxDec(0.0, refreshedDh.balance.toDouble()))
                    actvBalance.setText(refreshedDh.toString(), false)
                }
            }
        })
        vm.onGetMiningInfoFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
        vm.onTransferToUserSuccess.observe(viewLifecycleOwner, {
            showToast(getString(R.string.successfully_transfer))
            onBackPressed()
        })
        vm.onTransferToUserFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
        vm.onTransferToExchangeSuccess.observe(viewLifecycleOwner, {
            showToast(getString(R.string.successfully_transfer))
            onBackPressed()
        })
        vm.onTransferToExchangeFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }

    private fun onBalanceTypeSelected(dh: DH) {
        tietToresTransferToUser.filters =
            arrayOf(InputFilterMinMaxDec(0.0, dh.balance.toDouble()))

        if (tietToresTransferToUser.doubleValue() > dh.balance.toDouble()) {
            tietToresTransferToUser.setText(dh.balance)
        }
        vm.selectedBalanceType = dh.balanceType
    }
}