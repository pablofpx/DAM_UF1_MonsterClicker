package com.example.monster.recycler

import android.content.DialogInterface.OnClickListener
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.monster.databinding.UpgradeMenuItemBinding

class UpgradeViewHolder(view: View):RecyclerView.ViewHolder(view) {

    val binding = UpgradeMenuItemBinding.bind(view)

    fun render(upgradeItem: UpgradeItem, onClickListener: (UpgradeItem) -> Unit) {
        binding.upgradeImage.setImageResource(upgradeItem.icon)
        binding.upgradeText.text = upgradeItem.upgradeName
        binding.upgradeDescription.text = upgradeItem.description
        binding.upgradeCost.text = "${upgradeItem.cost}"

        itemView.setOnClickListener {
            onClickListener(upgradeItem)
        }
    }
}