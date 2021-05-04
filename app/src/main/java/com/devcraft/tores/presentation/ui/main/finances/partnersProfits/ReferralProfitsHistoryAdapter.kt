package com.devcraft.tores.presentation.ui.main.finances.partnersProfits

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.ReferralProfitsHistoryData
import kotlinx.android.synthetic.main.item_referral_profit_history.view.*


class ReferralProfitsHistoryAdapter :
    RecyclerView.Adapter<ReferralProfitsHistoryAdapter.ViewHolder>() {

    var items: MutableList<ReferralProfitsHistoryData.ReferralProfit> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_referral_profit_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ReferralProfitsHistoryData.ReferralProfit) {
            itemView.run {
                tvCreatedAt.text = item.createdAt
                tvLogin.text = item.login

                tvLevel.text = String.format(resources.getString(R.string.line), item.level)
                tvPartnerProfits.text = String.format(
                    resources.getString(R.string.tores_amount_str),
                    item.partnerProfit
                )
                tvReceived.text =
                    String.format(resources.getString(R.string.tores_amount), item.amount)
                tvReceivedInPercent.text = "(${item.percent}%)"
            }
        }
    }
}
