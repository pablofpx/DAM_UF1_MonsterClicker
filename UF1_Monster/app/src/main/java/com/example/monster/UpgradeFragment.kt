package com.example.monster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monster.databinding.FragmentUpgradeBinding
import com.example.monster.recycler.UpgradeAdapter
import com.example.monster.recycler.UpgradeItem
import com.example.monster.recycler.UpgradeProvider.Companion.upgradeItems
import com.example.monster.viewmodel.MonsterViewModel

class UpgradeFragment : Fragment() {
    var _binding: FragmentUpgradeBinding? = null
    val binding: FragmentUpgradeBinding
        get() = _binding!!
    val viewModel: MonsterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpgradeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.coins.observe(viewLifecycleOwner) { coins ->
            binding.upgradeMenuCoins.text = getString(R.string.coins, coins)
        }

//        viewModel.clickValue.observe(viewLifecycleOwner) { coins ->
//
//        }

        initRecyclerView()
        return view
    }

    fun initRecyclerView() {
        val manager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(requireContext(), manager.orientation) // creo que no funciona

        binding.upgradeRecycler.layoutManager = manager
        binding.upgradeRecycler.adapter = UpgradeAdapter(upgradeItems) {upgradeItem ->
            onItemSelected(upgradeItem)
        }
        binding.upgradeRecycler.addItemDecoration(decoration)
    }

    // hay que cambiar valores del coste ... etc
    private fun onItemSelected(upgradeItem: UpgradeItem){
        val index = upgradeItems.indexOfFirst { it.id == upgradeItem.id} // para coger los indices de la lista de provider

        if (index != -1 && viewModel.spendCoins(upgradeItem.cost)) {
            upgradeItems[index].upgradeEffect(viewModel)
            upgradeItems[index].cost += upgradeItems[index].costIncrement

            //se guarda aqui el estado
            viewModel.saveGameState(requireContext())
            binding.upgradeRecycler.adapter?.notifyItemChanged(index)

            Toast.makeText(activity, "${upgradeItem.upgradeName} upgraded!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Not enough coins...", Toast.LENGTH_SHORT).show()
        }
    }

    // esencial para guardar los datos
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