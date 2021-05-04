package com.devcraft.tores.presentation.ui.main.finances.mining

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.entities.MiningHistoryData
import com.devcraft.tores.entities.TransactionStatus
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.item_mining_history.view.*


class MiningHistoryAdapter : RecyclerView.Adapter<MiningHistoryAdapter.ViewHolder>() {

    var items: MutableList<MiningHistoryData.Transaction> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mining_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: MiningHistoryData.Transaction) {
            itemView.run {
                tvCreatedAt.text = item.createdAt
                var typeText = item.type.getTypeText(context)
                typeText += when (item.balance) {
                    BalanceType.BALANCE -> " " + context.getString(R.string.transaction_to_main_balance)
                    BalanceType.RANK_BALANCE -> " " + context.getString(R.string.transaction_to_rank_balance)
                    BalanceType.NOT_SPECIFIED -> ""
                }
                tvTransactionType.text = typeText
                tvToresAmount.text =
                    String.format(resources.getString(R.string.tores_amount), item.amount)

                llTimeLeft.setVisible(item.timeLeft != "-")
                if (item.timeLeft != "-") {
                    tvTimeLeft.text = item.timeLeft
                }

                tvStatus.setTextColor(item.transactionStatus.getStatusColor(context))
                tvStatus.text = item.transactionStatus.getStatusText(context)

                btnCancel.setVisible(item.type == MiningHistoryData.Transaction.Type.WITHDRAW_FROM_DEPOSIT && item.transactionStatus == TransactionStatus.AWAITING)
                btnCancel.setSafeOnClickListener {
                    callback?.onCancelWithdrawFromDepositClicked(item)
                }
            }
        }
    }

    interface Callback {
        fun onCancelWithdrawFromDepositClicked(transaction: MiningHistoryData.Transaction)
    }
}