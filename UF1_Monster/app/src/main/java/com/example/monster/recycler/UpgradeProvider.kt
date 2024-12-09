package com.example.monster.recycler

import android.util.Log
import com.example.monster.R
import com.example.monster.viewmodel.MonsterViewModel

class UpgradeProvider {
    companion object{
        val upgradeItems = listOf<UpgradeItem>(
            UpgradeItem(
                1,
                R.drawable.weapon_sword,
                "Sword damage +",
                "Boosts on hit coins!",
                5,
                2
            ) { monsterViewModel ->
                monsterViewModel.upgradeClickValue(1)
            },
            UpgradeItem(
                2,
                R.drawable.poison,
                "Poison flask",
                "Poison that makes the monster drop coins",
                20,
                5
            ) { monsterViewModel ->
                monsterViewModel.passiveUpgrade(1)
            },
            UpgradeItem(
                3,
                R.drawable.spellbound,
                "Magic spellbound",
                "Magically drops more coins...",
                100,
                15
            ) { monsterViewModel ->
                monsterViewModel.passiveUpgrade(10)
            },
            UpgradeItem(
                4,
                R.drawable.weapon_sword_upgraded,
                "Automatic sword",
                "Your sword hits by itself?",
                500,
                200
            ) { monsterViewModel ->
                monsterViewModel.passiveUpgrade(200)
            }
        )
    }
}