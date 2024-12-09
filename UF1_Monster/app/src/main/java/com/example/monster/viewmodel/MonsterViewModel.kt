package com.example.monster.viewmodel

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monster.R

class MonsterViewModel : ViewModel() {
    private val _coins = MutableLiveData<Long>(0)
    val coins: LiveData<Long> get() = _coins

    private val _clickValue = MutableLiveData<Long>(1)
    val clickValue: LiveData<Long> get() = _clickValue

    private val _passiveCoinsRate = MutableLiveData(0)
    val passiveCoinsRate: LiveData<Int> get() = _passiveCoinsRate

    private val _selectedMonster = MutableLiveData<Int>()
    val selectedMonster: LiveData<Int> get() = _selectedMonster

    fun passiveUpgrade(amountPerSecond: Int) {
        val currentRate = _passiveCoinsRate.value ?: 0
        _passiveCoinsRate.value = currentRate + amountPerSecond
    }

    fun calculateEarnings(lastLogout: Long) {
        val currentTime = System.currentTimeMillis()
        val timeElapsed = (currentTime - lastLogout) / 1000
        val earnings = timeElapsed * (passiveCoinsRate.value ?: 0)
        incrementCoins(earnings)
    }

    fun incrementCoins(amount: Long) {
        _coins.value = (_coins.value ?: 0) + amount
    }

    fun upgradeClickValue(amount: Long) {
//        val cost = _upgradeCost.value ?: 0
        val currentValue = _clickValue.value ?: 1
//        if (spendCoins(cost)) {
        Log.d("MonsterViewModel", "Before upgrade: Click Value = $currentValue")
        _clickValue.value = currentValue + amount
//            _upgradeCost.value = cost + 1
        Log.d("MonsterViewModel", "After upgrade: Click Value = ${_clickValue.value}")

    }

    fun spendCoins(cost: Int):Boolean {
        val currentCoins = _coins.value ?: 0
        if (currentCoins >= cost) {
            _coins.value = currentCoins - cost
            return true
        } else {
            return false
        }
    }

    fun selectMonster(imageResId: Int) {
        _selectedMonster.value = imageResId
    }

    // logica para guardar en sharedPreferences
    fun saveGameState(context: Context) {
        val sharedPreferences = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putLong("coins", _coins.value ?: 0)
        editor.putLong("click_value", _clickValue.value ?: 1)
        editor.putInt("passive_rate", _passiveCoinsRate.value ?: 0)
        editor.putLong("last_logout", System.currentTimeMillis())
        editor.putInt("current_image", _selectedMonster.value ?: R.drawable.weapon_hand)

        editor.apply()
    }

    fun loadGameState(context: Context){
        val sharedPreferences = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val savedCoins = sharedPreferences.getLong("coins", 0)
        val savedClickValue = sharedPreferences.getLong("click_value", 1)
        val savedPassiveRate = sharedPreferences.getInt("passive_rate", 0)
        val lastLogout = sharedPreferences.getLong("last_logout", System.currentTimeMillis())
        val savedImage = sharedPreferences.getInt("current_image", R.drawable.weapon_hand)

        _coins.value = savedCoins
        _clickValue.value = savedClickValue
        _passiveCoinsRate.value = savedPassiveRate
        _selectedMonster.value = savedImage

       calculateEarnings(lastLogout)
    }

    fun resetGame(context: Context) {
        _coins.value = 0
        _clickValue.value = 1
        _passiveCoinsRate.value = 0
        _selectedMonster.value = R.drawable.weapon_hand

        val sharedPreferences = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}