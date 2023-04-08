package br.edu.ufabc.nefrobalance.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.edu.ufabc.nefrobalance.databinding.FragmentHomeBinding

class FragmentHome: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.floatingActionButtonWater.setOnClickListener {
            Log.d(javaClass.simpleName, "USER CLICKED TO ADD WATER")
            findNavController().navigate(FragmentHomeDirections.commitFood(foodId = -1, foodName = "Water", isLiquid = true))
        }

        binding.floatingActionButtonReset.setOnClickListener {
            Log.d(javaClass.simpleName, "USER CLICKED TO RESET")
            findNavController().navigate(FragmentHomeDirections.resetCounters())
        }

        return binding.root
    }
}