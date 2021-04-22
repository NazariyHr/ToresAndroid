package com.devcraft.tores.presentation.ui.main.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.ProfitsAndRegisters
import kotlinx.android.synthetic.main.item_profit.view.*

class ProfitsAdapter : RecyclerView.Adapter<ProfitsAdapter.ViewHolder>() {

    var items: MutableList<ProfitsAndRegisters.Profit> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ProfitsAndRegisters.Profit) {
            itemView.run {
                tvDate.text = String.format(
                    resources.getString(R.string.in_date_and_time),
                    item.createdAtDate,
                    item.createdAtTime
                )
                tvProfit.text =
                    String.format(resources.getString(R.string.plus_tores_amount), item.profit)
            }
        }
    }
}
