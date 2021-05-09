package com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.app.App
import com.devcraft.tores.entities.TopupsAndWithdrawalsData
import com.devcraft.tores.entities.TransactionStatus
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import com.devcraft.tores.utils.functions.initCountdownTimerWithWithTimeFormattingTick
import kotlinx.android.synthetic.main.item_topup_or_withdrawal.view.*


class TopupsAndWithdrawalsAdapter : RecyclerView.Adapter<TopupsAndWithdrawalsAdapter.ViewHolder>() {

    var items: MutableList<TopupsAndWithdrawalsData.Transaction> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            timersForItems.forEach {
                it.value.cancel()
            }
            timersForItems.clear()
            notifyDataSetChanged()
        }

    var timersForItems = mutableMapOf<TopupsAndWithdrawalsData.Transaction, CountDownTimer>()

    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_topup_or_withdrawal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bindTime(payloads.first() as String)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var timer: CountDownTimer? = null

        @SuppressLint("SetTextI18n")
        fun bind(item: TopupsAndWithdrawalsData.Transaction) {
            itemView.run {
                tvCreatedAt.text = item.createdAt
                showTransactionType(item)
                tvToresAmount.text =
                    String.format(resources.getString(R.string.tores_amount), item.amount)
                item.currency.getCurrencyDrawable(context).let {
                    ivCurrency.setImageDrawable(it)
                    ivCurrency.setVisible(it != null)
                }
                tvCurrencyAmount.text = item.amountInCurrency.toString()
                showStatus(item)

                cvItem.setSafeOnClickListener {
                    callback?.onTransactionClicked(item)
                }
            }
        }

        fun bindTime(formattedTime: String) {
            itemView.tvWaitingTime.text = formattedTime
        }

        private fun showTransactionType(item: TopupsAndWithdrawalsData.Transaction) {
            itemView.run {
                when (item.type) {
                    TopupsAndWithdrawalsData.Transaction.Type.TOPUP -> {
                        ivTransactionType.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_arrow_up_filled_green
                            )
                        )
                        tvTransactionType.text = resources.getString(R.string.buying)
                    }
                    TopupsAndWithdrawalsData.Transaction.Type.WITHDRAWAL -> {
                        ivTransactionType.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_arrow_down_red_filled
                            )
                        )
                        tvTransactionType.text = resources.getString(R.string.selling)
                    }
                }
            }
        }

        private fun showStatus(item: TopupsAndWithdrawalsData.Transaction) {
            itemView.run {
                if (item.transactionStatus == TransactionStatus.CREATED && item.remaining > 0) {
                    tvWaitingTime.setVisible()
                    tvWaitingTime.text = ""
                    startTimerForItem(item)
                } else {
                    tvWaitingTime.setGone()
                }
                tvStatus.setTextColor(item.transactionStatus.getStatusColor(context))
                tvStatus.text = item.transactionStatus.getStatusText(context, true)
            }
        }
    }

    fun startTimerForItem(item: TopupsAndWithdrawalsData.Transaction) {
        timersForItems[item]?.cancel()
        initCountdownTimerWithWithTimeFormattingTick(
            item.remaining * 1000, 1000, { formattedTime ->
                notifyItemChanged(items.indexOf(item), formattedTime)
            }, {
                notifyItemChanged(
                    items.indexOf(item),
                    App.instance.getString(R.string.time_is_over)
                )
            }, { timer ->
                timersForItems[item] = timer
            }
        )
    }

    interface Callback {
        fun onTransactionClicked(transaction: TopupsAndWithdrawalsData.Transaction)
    }
}