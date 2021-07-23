package com.devcraft.tores.presentation.ui.main.more.ranks.about

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_about_ranks.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutRanksFragment : BaseFragment(R.layout.fragment_about_ranks) {
    override val vm: MainViewModel by viewModel()

    override fun initViews() {
        setBaseActivityToolbarTitle(getString(R.string.about_ranks))
        showBaseActivityBackButton()
    }

    override fun initListeners() {
        super.initListeners()
        val txt = getString(R.string.about_ranks_part_3)
        val ss = SpannableString(txt)

        val clickableSpanTelegram: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                openTelegram(getString(R.string.telegram_tores_coin))
            }
        }
        val clickableSpanEmail: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                openEmail(getString(R.string.tores_email))
            }
        }

        val telegram = "@ToresCoin"
        val email = "toresbusiness.help@gmail.com"
        val telegramStartIndex = txt.indexOf(telegram)
        val telegramEndIndex = txt.indexOf(telegram) + telegram.length
        val emailStartIndex = txt.indexOf(email)
        val emailEndIndex = txt.indexOf(email) + email.length

        ss.setSpan(
            clickableSpanTelegram,
            telegramStartIndex,
            telegramEndIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ss.setSpan(
            clickableSpanEmail,
            emailStartIndex,
            emailEndIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvPart3.text = ss
        tvPart3.movementMethod = LinkMovementMethod.getInstance()
        tvPart3.highlightColor = Color.TRANSPARENT
    }
}