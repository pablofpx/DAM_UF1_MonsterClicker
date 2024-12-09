package com.example.monster

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.monster.databinding.FragmentMonsterBinding
import com.example.monster.viewmodel.MonsterViewModel
import java.text.DecimalFormat

class MonsterFragment : Fragment() {
    var _binding: FragmentMonsterBinding? = null
    val binding: FragmentMonsterBinding
        get() = _binding!!
    val viewModel: MonsterViewModel by activityViewModels()

    private val handler = Handler(Looper.getMainLooper())
    private var passiveIncomeRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMonsterBinding.inflate(inflater, container, false)
        val view = binding.root
        val coinsTextView = binding.topmenuQuantity
        val monsterClicked = binding.monsterLayout
        val deleteOption = binding.topmenuOptions

        viewModel.coins.observe(viewLifecycleOwner) { coins ->
            coinsTextView.text = formatCoins(coins)
        }

        // debugear value
        viewModel.clickValue.observe(viewLifecycleOwner) { newClickValue ->
            Log.d("UpgradeFragment", "Updated Click Value: $newClickValue")
        }

        viewModel.selectedMonster.observe(viewLifecycleOwner) { imageResId ->
            binding.monsterMonster.setImageResource(imageResId)
        }

        monsterClicked.setOnClickListener {
            val clickValue = viewModel.clickValue.value ?: 1
            viewModel.incrementCoins(clickValue)
        }

        // opcion de borrar partida con dialog
        deleteOption.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Options")
                .setMessage("Do you want to delete your progress?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.resetGame(requireContext())
                    Toast.makeText(requireContext(), "Game reseted...", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
        }

        startPassiveIncomeUpdates()

        return view
    }

    private fun startPassiveIncomeUpdates() {
        passiveIncomeRunnable = object : Runnable {
            override fun run() {
                // cada segundo
                val passiveRate = viewModel.passiveCoinsRate.value ?: 0
                if(passiveRate > 0) {
                    viewModel.incrementCoins(passiveRate.toLong())
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(passiveIncomeRunnable!!)
    }

    fun formatCoins(value: Long): String {
        val decimalFormat = DecimalFormat("#,###")
        return when {
            value >= 1_000_000_000_000 -> String.format("%.3fT", value / 1_000_000_000_000.0)
            value >= 1_000_000_000 -> String.format("%.3fB", value / 1_000_000_000.0)
            value >= 1_000_000 -> String.format("%.3fM", value / 1_000_000.0)
            value >= 1_000 -> decimalFormat.format(value)
            else -> value.toString()
        }
    }

    // guarda cuadno se pausa
    override fun onPause() {
        super.onPause()
        viewModel.saveGameState(requireContext())
    }

    // cargar los datos
    override fun onResume() {
        super.onResume()
        viewModel.loadGameState(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}