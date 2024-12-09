package com.example.monster.recycler

import com.example.monster.viewmodel.MonsterViewModel

data class UpgradeItem(
    val id: Int,
    val icon: Int,
    val upgradeName: String,
    val description: String,
    var cost: Int,
    val costIncrement: Int,
    val upgradeEffect: (MonsterViewModel) -> Unit,
)