package com.example.monster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.monster.databinding.FragmentQuestBinding
import com.example.monster.databinding.FragmentUpgradeBinding
import com.example.monster.viewmodel.MonsterViewModel

class QuestFragment : Fragment() {
    private var _binding: FragmentQuestBinding? = null
    private val binding get() = _binding!!
    val viewModel: MonsterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuestBinding.inflate(inflater, container, false)
        val view = binding.root

        val image1 = binding.questStar
        val image2 = binding.questPakman
        val image3 = binding.questGhost
        val image4 = binding.questIncognito

        image1.setOnClickListener {
            viewModel.selectMonster(R.drawable.ghost_star)
            viewModel.saveGameState(requireContext())
            Toast.makeText(activity, "Monster has changed!", Toast.LENGTH_SHORT).show()
        }
        image2.setOnClickListener {
            viewModel.selectMonster(R.drawable.physical_pakman)
            viewModel.saveGameState(requireContext())
            Toast.makeText(activity, "Monster has changed!", Toast.LENGTH_SHORT).show()
        }
        image3.setOnClickListener {
            viewModel.selectMonster(R.drawable.ghost_squid)
            viewModel.saveGameState(requireContext())
            Toast.makeText(activity, "Monster has changed!", Toast.LENGTH_SHORT).show()
        }
        image4.setOnClickListener {
            viewModel.selectMonster(R.drawable.weapon_hand)
            viewModel.saveGameState(requireContext())
            Toast.makeText(activity, "Monster has changed!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}