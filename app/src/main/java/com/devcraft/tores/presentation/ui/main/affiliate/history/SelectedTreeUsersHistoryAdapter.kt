package com.devcraft.tores.presentation.ui.main.affiliate.history

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.tores.R
import com.devcraft.tores.entities.AffiliateTreeUser
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.item_selected_tree_users_history.view.*

class SelectedTreeUsersHistoryAdapter :
    RecyclerView.Adapter<SelectedTreeUsersHistoryAdapter.ViewHolder>() {

    var items: MutableList<AffiliateTreeUser> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_selected_tree_users_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.bind(null)
        } else {
            holder.bind(items[position - 1])
        }
    }

    override fun getItemCount(): Int = items.size + 1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AffiliateTreeUser?) {
            itemView.run {
                if (item == null) {
                    ivArrow.setGone()
                    tvLogin.setGone()
                    tvLine.text = "Line 1"
                    tvLine.setTextColor(Color.parseColor("green"))

                    root.setSafeOnClickListener {
                        callback?.onFirstLineClicked()
                    }
                } else {
                    ivArrow.setVisible()
                    tvLogin.setVisible()
                    tvLogin.text = item.login
                    tvLine.text = "Line ${item.line}"
                    tvLine.setTextColor(Color.parseColor(item.colorClass))

                    root.setSafeOnClickListener {
                        callback?.onUserClick(item)
                    }
                }
            }
        }
    }

    interface Callback {
        fun onFirstLineClicked()
        fun onUserClick(user: AffiliateTreeUser)
    }
}
