package com.devcraft.tores.presentation.ui.main.mining.addToMining.balanceAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import kotlinx.android.synthetic.main.item_dropdown_balance.view.*

class VH(val itemView: View) {
    fun bind(item: DH) {
        itemView.run {
            tvBalance.text = item.toString()
        }
    }
}