package com.devcraft.tores.presentation.ui.main.more.partners.ourPartners

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.Partner
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.item_our_partner.view.*

class PartnersAdapter :
    RecyclerView.Adapter<PartnersAdapter.ViewHolder>() {

    var items: MutableList<Partner> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_our_partner, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Partner) {
            itemView.run {
                tvCompanyName.text = item.companyName
                if (item.percent != null) {
                    tvPercent.setVisible()
                    tvPercent.text = "${item.percent}%"
                } else {
                    tvPercent.setGone()
                }
                tvLink.text = item.url
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
