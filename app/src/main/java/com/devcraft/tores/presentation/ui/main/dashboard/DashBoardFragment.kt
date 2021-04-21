package com.devcraft.tores.presentation.ui.main.dashboard

import android.graphics.drawable.Drawable
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashBoardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val vm: MainViewModel by sharedViewModel()

    lateinit var selectedDrawable: Drawable

    override fun initViews() {
        selectedDrawable = btnByPartners.background
        btnByPartners.setOnClickListener {
            btnByPartners.background = selectedDrawable
            btnByPartners.setTextColor(requireContext().resources.getColor(R.color.black))
            btnByRanks.background = null
            btnByRanks.setTextColor(requireContext().resources.getColor(R.color.white))
        }
        btnByRanks.setOnClickListener {
            btnByRanks.background = selectedDrawable
            btnByRanks.setTextColor(requireContext().resources.getColor(R.color.black))
            btnByPartners.background = null
            btnByPartners.setTextColor(requireContext().resources.getColor(R.color.white))
        }
    }
}