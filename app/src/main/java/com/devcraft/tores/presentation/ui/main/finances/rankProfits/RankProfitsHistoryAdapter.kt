package com.devcraft.tores.presentation.ui.main.finances.rankProfits

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.RankProfitsHistoryData
import kotlinx.android.synthetic.main.item_rank_profit_history.view.*


class RankProfitsHistoryAdapter :
    RecyclerView.Adapter<RankProfitsHistoryAdapter.ViewHolder>() {

    var items: MutableList<RankProfitsHistoryData.RankProfit> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rank_profit_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: RankProfitsHistoryData.RankProfit) {
            itemView.run {
                tvCreatedAt.text = item.createdAt
                tvProfitAmount.text =
                    String.format(resources.getString(R.string.tores_amount), item.amount)
                tvRank.text = item.rank.getStatusText(context)
            }
        }
    }
}
