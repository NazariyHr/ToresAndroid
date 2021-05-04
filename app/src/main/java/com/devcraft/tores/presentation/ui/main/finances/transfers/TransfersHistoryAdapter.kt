package com.devcraft.tores.presentation.ui.main.finances.transfers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.entities.TransactionStatus
import com.devcraft.tores.entities.TransfersHistoryData
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.item_transfer_history.view.*


class TransfersHistoryAdapter : RecyclerView.Adapter<TransfersHistoryAdapter.ViewHolder>() {

    var items: MutableList<TransfersHistoryData.Transaction> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transfer_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TransfersHistoryData.Transaction) {
            itemView.run {
                tvCreatedAt.text = item.createdAt

                showTransactionType(item)
                tvToresAmount.text =
                    String.format(resources.getString(R.string.tores_amount), item.amount)

                tvLogin.text = item.login

                tvStatus.setTextColor(item.transactionStatus.getStatusColor(context))
                tvStatus.text = item.transactionStatus.getStatusText(context)


                btnEnterTheCode.setVisible(item.transactionStatus == TransactionStatus.WAITING_TAC)
                btnEnterTheCode.setSafeOnClickListener {
                    callback?.onEnterTheCodeClicked(item)
                }
            }
        }

        private fun showTransactionType(item: TransfersHistoryData.Transaction) {
            itemView.run {
                when (item.type) {
                    TransfersHistoryData.Transaction.Type.RECEIVED_FROM_USER -> {
                        ivTransactionType.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_arrow_left_filled_green
                            )
                        )
                    }
                    TransfersHistoryData.Transaction.Type.TRANSFER_TO_EXCHANGE -> {
                        ivTransactionType.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_arrow_up_filled_green
                            )
                        )
                    }
                    TransfersHistoryData.Transaction.Type.TRANSFER_TO_USER -> {
                        ivTransactionType.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_arrow_right_filled_green
                            )
                        )
                    }
                }

                var typeText = item.type.getTypeText(context)
                typeText += when (item.balance) {
                    BalanceType.BALANCE -> " " + context.getString(R.string.transaction_to_main_balance)
                    BalanceType.RANK_BALANCE -> " " + context.getString(R.string.transaction_to_rank_balance)
                    BalanceType.NOT_SPECIFIED -> ""
                }
                tvTransactionType.text = typeText
            }
        }
    }

    interface Callback {
        fun onEnterTheCodeClicked(transaction: TransfersHistoryData.Transaction)
    }
}