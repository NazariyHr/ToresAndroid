package com.devcraft.tores.presentation.ui.main.more.partners.becomePartner

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseDialogFragment
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.dialog_copy_button_url.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CopyButtonUrlDialog : BaseDialogFragment(R.layout.dialog_copy_button_url) {

    override val vm: BecomePartnerViewModel by viewModel()

    var callback: Callback? = null

    override fun initListeners() {
        super.initListeners()
        llClose.setSafeOnClickListener {
            dismiss()
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
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback?.onCloseDialog()
    }

    interface Callback {
        fun onCloseDialog()
    }

    companion object {
        fun newInstance(
            callback: Callback?
        ): CopyButtonUrlDialog {
            val f = CopyButtonUrlDialog()
            f.callback = callback
            return f
        }
    }
}