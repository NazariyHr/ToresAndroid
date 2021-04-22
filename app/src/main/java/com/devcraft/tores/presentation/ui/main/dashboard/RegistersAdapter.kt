package com.devcraft.tores.presentation.ui.main.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.ProfitsAndRegisters
import kotlinx.android.synthetic.main.item_profit.view.*
import kotlinx.android.synthetic.main.item_registered.view.*

class RegistersAdapter : RecyclerView.Adapter<RegistersAdapter.ViewHolder>() {

    var items: MutableList<ProfitsAndRegisters.Registered> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_registered, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ProfitsAndRegisters.Registered) {
            itemView.run {
                tvLogin.text = item.login
                tvDateAndTime.text =
                    String.format(
                        resources.getString(R.string.in_date_and_time),
                        item.createdAtDate,
                        item.createdAtTime
                    )
            }
        }
    }
}
