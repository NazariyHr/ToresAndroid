package com.devcraft.tores.presentation.ui.main.shares.sharesHistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.ShareTransfer
import com.devcraft.tores.entities.ShareTransferType
import kotlinx.android.synthetic.main.item_share_history.view.*

class SharesHistoryAdapter : RecyclerView.Adapter<SharesHistoryAdapter.ViewHolder>() {

    var items: MutableList<ShareTransfer> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_share_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ShareTransfer) {
            itemView.run {
                tvCreatedAt.text = item.createdAt
                tvId.text = item.id
                showTransactionType(item)
                tvToresAmount.text =
                    String.format(resources.getString(R.string.tores_amount_str), item.amount)
                tvSharesAmount.text =
                    String.format(
                        resources.getString(R.string.shares_amount_str),
                        item.sharesAmount
                    )
                tvStatus.setTextColor(item.status.getStatusColor(context))
                tvStatus.text = item.status.getStatusText(context)
            }
        }

        private fun showTransactionType(item: ShareTransfer) {
            itemView.run {
                ivTransactionType.setImageDrawable(item.type.getTypeDrawable(context))
                var typeText = item.type.getTypeText(context)
                if (item.type == ShareTransferType.TRANSFER_SHARES && item.login != null) {
                    typeText += " ${item.login}"
                }
                tvTransactionType.text = typeText
            }
        }
    }
}