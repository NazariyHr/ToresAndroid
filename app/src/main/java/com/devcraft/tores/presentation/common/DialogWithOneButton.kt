package com.devcraft.tores.presentation.common

import android.app.Dialog
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.devcraft.tores.R
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.google.android.material.button.MaterialButton

class DialogWithOneButton : DialogFragment() {

    companion object {
        fun newInstance(
            callback: Callback?,
            title: String?,
            message: String?,
            btnTitle: String?
        ): DialogWithOneButton {
            val f = DialogWithOneButton()
            f.callback = callback
            f.title = title
            f.message = message
            f.btnTitle = btnTitle
            return f
        }

        fun newInstance(
            callback: Callback?,
            title: String?,
            message: Spanned?,
            btnTitle: String?
        ): DialogWithOneButton {
            val f = DialogWithOneButton()
            f.callback = callback
            f.title = title
            f.messageSpanned = message
            f.btnTitle = btnTitle
            return f
        }
    }

    var callback: Callback? = null
    var title: String? = null
    var message: String? = null
    var messageSpanned: Spanned? = null
    var btnTitle: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_round)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.dialog_with_button, container, false)
        v.findViewById<MaterialButton>(R.id.button).setSafeOnClickListener {
            callback?.onBtnClicked()
            dismiss()
        }
        v.findViewById<LinearLayout>(R.id.llClose).setSafeOnClickListener {
            callback?.onCloseClicked()
            dismiss()
        }
        title?.let {
            v.findViewById<TextView>(R.id.tvTitle).text = it
        }
        message?.let {
            v.findViewById<TextView>(R.id.tvMessage).text = it
        }
        messageSpanned?.let {
            v.findViewById<TextView>(R.id.tvMessage).text = it
        }
        btnTitle?.let {
            v.findViewById<MaterialButton>(R.id.button).text = it
        }

        return v
    }

    interface Callback {
        fun onBtnClicked()
        fun onCloseClicked()
    }
}