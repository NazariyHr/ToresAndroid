package com.devcraft.tores.presentation.ui.main.finances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import kotlinx.android.synthetic.main.item_tab_title.view.*

class TabTitlesAdapter : RecyclerView.Adapter<TabTitlesAdapter.ViewHolder>() {

    private var items: MutableList<TabList> = mutableListOf(
        TabList.TOPUPS_AND_WITHDRAWAL,
        TabList.MINING,
        TabList.TRANSFERS,
        TabList.PARTNERS,
        TabList.BONUSES
    )
    private var selectedItem: TabList = TabList.TOPUPS_AND_WITHDRAWAL

    var callback: Callback? = null
        set(value) {
            field = value
            value?.onTabClicked(selectedItem)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tab_title, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: TabList) {
            itemView.run {
                when (item) {
                    TabList.TOPUPS_AND_WITHDRAWAL -> {
                        ivIcon.setImageResource(R.drawable.ic_topups_and_withdrawal)
                        tvTitle.setText(R.string.topups_and_withdrawals)
                    }

                    TabList.MINING -> {
                        ivIcon.setImageResource(R.drawable.ic_mining_connect)
                        tvTitle.setText(R.string.mining)
                    }

                    TabList.TRANSFERS -> {
                        ivIcon.setImageResource(R.drawable.ic_transfers)
                        tvTitle.setText(R.string.transfers)
                    }
                    TabList.PARTNERS -> {
                        ivIcon.setImageResource(R.drawable.ic_partner_rewards)
                        tvTitle.setText(R.string.partner_rewards)
                    }

                    TabList.BONUSES -> {
                        ivIcon.setImageResource(R.drawable.ic_bonuses)
                        tvTitle.setText(R.string.bonuse_rewards)
                    }
                }

                root.isSelected = item == selectedItem
                if (item == selectedItem) {
                    ivIcon.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.dark_primary_color
                        )
                    )
                    tvTitle.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.dark_primary_color
                        )
                    )
                } else {
                    ivIcon.clearColorFilter()
                    tvTitle.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.text_color
                        )
                    )
                }

                setOnClickListener {
                    val oldSelectedIndex = items.indexOf(selectedItem)
                    val currIndex = items.indexOf(item)
                    selectedItem = item
                    notifyItemChanged(oldSelectedIndex)
                    notifyItemChanged(currIndex)
                    callback?.onTabClicked(item)
                }
            }
        }
    }

    interface Callback {
        fun onTabClicked(tab: TabList)
    }
}
