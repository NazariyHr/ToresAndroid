package com.devcraft.tores.presentation.ui.main.more.partners.userRequests

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.BecomePartnerRequest
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.item_become_partner_request.view.*

class BecomePartnerRequestsAdapter :
    RecyclerView.Adapter<BecomePartnerRequestsAdapter.ViewHolder>() {

    var items: MutableList<BecomePartnerRequest> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_become_partner_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: BecomePartnerRequest) {
            itemView.run {
                tvCreatedAt.text = item.createdAt
                tvCompanyName.text = item.companyName
                tvLink.text = item.url
                tvStatus.setTextColor(item.status.getStatusColor(context))
                tvStatus.text = item.status.getStatusText(context)

                tvLink.setSafeOnClickListener {
                    callback?.onUrlClicked(item.url)
                }
            }
        }
    }

    interface Callback {
        fun onUrlClicked(url: String)
    }
}
