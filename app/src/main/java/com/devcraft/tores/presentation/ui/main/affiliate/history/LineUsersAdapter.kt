package com.devcraft.tores.presentation.ui.main.affiliate.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.AffiliateTreeUser
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.item_affiliate_tree_users_line.view.*

class LineUsersAdapter :
    RecyclerView.Adapter<LineUsersAdapter.ViewHolder>() {

    var items: MutableList<AffiliateTreeUser> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_affiliate_tree_users_line, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AffiliateTreeUser) {
            itemView.run {
                tvRegisterDate.text = item.registeredAtDate + " " + item.registeredAtTime
                tvLogin.text = item.login
                tvPercent.text = "${item.percent}%"
                tvProfitFromUser.text =
                    String.format(resources.getString(R.string.tores_amount), item.profit)
                tvSendToMining.text =
                    String.format(resources.getString(R.string.tores_amount), item.deposit)

                cvItem.setSafeOnClickListener {
                    callback?.onUserInLineClick(item)
                }
            }
        }
    }

    interface Callback {
        fun onUserInLineClick(user: AffiliateTreeUser)
    }
}
