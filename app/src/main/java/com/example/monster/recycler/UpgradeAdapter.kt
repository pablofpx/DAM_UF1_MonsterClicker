package com.example.monster.recycler

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monster.R

class UpgradeAdapter(private val upgradeItems: List<UpgradeItem>, private val onClickListener: (UpgradeItem) -> Unit) :
    RecyclerView.Adapter<UpgradeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpgradeViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.upgrade_menu_item, parent, false)
        return UpgradeViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpgradeViewHolder, position: Int) {
        val item = upgradeItems[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = upgradeItems.size
}