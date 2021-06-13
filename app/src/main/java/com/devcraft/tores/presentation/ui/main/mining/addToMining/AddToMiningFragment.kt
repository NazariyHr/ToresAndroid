package com.devcraft.tores.presentation.ui.main.mining.addToMining

import android.view.View
import android.widget.AdapterView
import androidx.core.widget.doAfterTextChanged
import com.devcraft.tores.R
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.mining.MiningViewModel
import com.devcraft.tores.presentation.ui.main.mining.addToMining.balanceAdapter.BalanceListAdapter
import com.devcraft.tores.presentation.ui.main.mining.addToMining.balanceAdapter.DH
import com.devcraft.tores.utils.extensions.*
import com.devcraft.tores.utils.inputFilters.InputFilterMinMaxDec
import kotlinx.android.synthetic.main.fragment_add_to_mining.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddToMiningFragment : BaseFragment(R.layout.fragment_add_to_mining) {

    override val vm: AddToMiningViewModel by sharedViewModel()
    private val vmMining: MiningViewModel by sharedViewModel()

    private lateinit var adapter: BalanceListAdapter

    override fun initViews() {
        super.initViews()
        setBaseActivityToolbarTitle(getString(R.string.adding_in_mining))
        showBaseActivityBackButton()
        attachKeyboardListener()
    }

    override fun onDetach() {
        super.onDetach()
        hideKeyboard()
        detachKeyboardListener()
    }

    override fun initListeners() {
        super.initListeners()

        swipeRefresh.setOnRefreshListener {
            vmMining.refreshData()
        }
        actvBalance.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selected = adapter.getItem(position)
                selected?.let {
                    onBalanceTypeSelected(it)
                }
            }
        tietToresToAdd.doAfterTextChanged {
            btnAddToMining.isEnabled = !it.isNullOrEmpty()
            val needToScrollToBottom = llToresIn.visibility == View.GONE && !it.isNullOrEmpty()
            llToresIn.setVisible(!it.isNullOrEmpty())
            if (needToScrollToBottom) {
                nsv.post {
                    nsv.fullScroll(View.FOCUS_DOWN)
                    tietToresToAdd.requestFocus()
                    tietToresToAdd.setSelection(tietToresToAdd.text.toString().length)
                }
            }

            val enteredTores = tietToresToAdd.doubleValue()
            tvToresInDayAfterAdd.text = (enteredTores * 0.01).toString()
            tvToresInYearAfterAdd.text = (enteredTores * 0.01 * 365).toString()
        }
        btnAddToMining.setSafeOnClickListener {
            vm.addToMining(tietToresToAdd.doubleValue())
        }
    }

    override fun initObservers() {
        super.initObservers()

        vm.someProcessAlive.combineWith(vmMining.someProcessAlive) { v1, v2 -> v1 == true || v2 == true }
            .observe(viewLifecycleOwner, {
                if (swipeRefresh.isRefreshing && !it) {
                    swipeRefresh.isRefreshing = it
                }
                progress_overlay.setVisible(it)
            })
        vmMining.miningInfo.observe(viewLifecycleOwner, { mInfo ->
            tvLoadingDataError.setGone()
            llAddToMining.setVisible()

            tvAvailableBalanceForMining.text =
                getString(R.string.tores_amount, mInfo.availableToMining)

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
                    tietToresToAdd.filters =
                        arrayOf(InputFilterMinMaxDec(0.0, refreshedDh.balance.toDouble()))
                    actvBalance.setText(refreshedDh.toString(), false)
                }
            }
        })
        vmMining.onGetMiningInfoFailure.observe(viewLifecycleOwner, {
            llAddToMining.setGone()
            tvLoadingDataError.setVisible()
            showToast(it)
        })
        vm.onAddToMiningSuccess.observe(viewLifecycleOwner, {
            showToast(getString(R.string.successfully_added_to_mining))
            vmMining.refreshData()
            onBackPressed()
        })
        vm.onAddToMiningFailure.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }

    private fun onBalanceTypeSelected(dh: DH) {
        tietToresToAdd.filters =
            arrayOf(InputFilterMinMaxDec(0.0, dh.balance.toDouble()))
        vm.selectedBalanceType = dh.balanceType
    }
}
