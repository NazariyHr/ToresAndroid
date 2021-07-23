package com.devcraft.tores.presentation.ui.main.more.ranks.rankSystem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.RankSystemLevel
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.item_rank_system_level.view.*

class RankSystemLevelAdapter : RecyclerView.Adapter<RankSystemLevelAdapter.ViewHolder>() {

    var items: MutableList<RankSystemLevel> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rank_system_level, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(item: RankSystemLevel) {
            itemView.run {
                item.rank.getRankDrawable(context).let { rankIcon ->
                    ivRankBadge.setImageDrawable(rankIcon)
                    ivRankBadge.setVisible(rankIcon != null)
                }
                tvRankTitle.text = item.rank.getRankText(context)
                tvDeposit.text = item.deposit ?: "-"
                tvYourReferralLevel.text = item.referralLevel ?: "-"
                tvMarketingBaseTores.text = item.marketingBase ?: "-"
                tvPayments.text = item.payments ?: "-"
                tvConditions.text = item.conditions ?: "-"
            }
        }
    }
}
