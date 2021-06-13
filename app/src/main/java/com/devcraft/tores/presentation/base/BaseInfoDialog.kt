package com.devcraft.tores.presentation.base

import com.devcraft.tores.R
import com.devcraft.tores.presentation.ui.main.MainViewModel
import com.devcraft.tores.utils.MetricsUtils
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.dialog_base_informational.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BaseInfoDialog(val content: String) :
    BaseDialogFragment(R.layout.dialog_base_informational) {
    override val vm: MainViewModel by sharedViewModel()

    override fun initViews() {
        super.initViews()

        tvContent.text = content

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