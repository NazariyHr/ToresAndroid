package com.devcraft.tores.presentation.common.adapter.balanceAdapter

import android.view.View
import kotlinx.android.synthetic.main.item_dropdown_balance.view.*

class VH(val itemView: View) {
    fun bind(item: DH) {
        itemView.run {
            tvBalance.text = item.toString()
        }
    }
}