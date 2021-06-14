package com.devcraft.tores.presentation.ui.main.dashboard.currencyListAdapter

import android.view.View
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.item_dropdown_currency.view.*

class VH(val itemView: View) {
    fun bind(item: DH) {
        itemView.run {
            tvCurrencyTitle.text = item.toString()
            item.currency.getCurrencyDrawable(this.context).let { d ->
                if (d == null) {
                    ivCurrencyIcon.setGone()
                } else {
                    ivCurrencyIcon.setVisible()
                    ivCurrencyIcon.setImageDrawable(d)
                }
            }
        }
    }
}