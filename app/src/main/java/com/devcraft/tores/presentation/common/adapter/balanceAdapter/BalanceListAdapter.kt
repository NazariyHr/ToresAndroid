package com.devcraft.tores.presentation.common.adapter.balanceAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.devcraft.tores.R

class BalanceListAdapter(c: Context, val items: MutableList<DH>) : ArrayAdapter<DH>(c, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView
        if (v == null) {
            val ll = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = ll.inflate(R.layout.item_dropdown_balance, null)
        }
        val dh: DH = items[position]
        val vh = VH(v!!)
        vh.bind(dh)
        return v
    }
}