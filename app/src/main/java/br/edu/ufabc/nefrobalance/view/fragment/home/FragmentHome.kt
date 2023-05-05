package br.edu.ufabc.nefrobalance.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.edu.ufabc.nefrobalance.R
import br.edu.ufabc.nefrobalance.databinding.FragmentHomeBinding
import br.edu.ufabc.nefrobalance.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FragmentHome: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        bindEvens()

        return binding.root
    }

    private fun bindEvens() {
        syncConsumedCountersWithRepository()

        mainViewModel.consumedCounter.observe(viewLifecycleOwner) {
            binding.textLiquidQuantityValue.text = getString(R.string.text_quantity_value_liquid_default, it.liquid)
            binding.textPhosphorusQuantityValue.text = getString(R.string.text_quantity_value_food_default, it.phosphor)
            binding.textSodiumQuantityValue.text = getString(R.string.text_quantity_value_food_default, it.sodium)
            binding.textPotassiumQuantityValue.text = getString(R.string.text_quantity_value_food_default, it.potassium)
        }

        binding.floatingActionButtonWater.setOnClickListener {
            findNavController().navigate(
                FragmentHomeDirections.commitFood(
                    foodId = -1,
                    foodName = getString(R.string.water_name),
                    isLiquid = true
                )
            )
        }

        binding.floatingActionButtonReset.setOnClickListener {
            findNavController().navigate(FragmentHomeDirections.resetCounters())
        }

        mainViewModel.incrementCounters.observe(viewLifecycleOwner) {
            it?.let {
                mainViewModel.incrementCountersInRepository().observe(viewLifecycleOwner) { status ->
                    when(status) {
                        is MainViewModel.Status.Failure -> {
                            Snackbar.make(
                                binding.root,
                                getString(R.string.dialog_food_commit_invalid_input),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        is MainViewModel.Status.Success -> {
                            syncConsumedCountersWithRepository()
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.food_commit_added),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun syncConsumedCountersWithRepository() {
        mainViewModel.getLatestConsumedCounter().observe(viewLifecycleOwner) { status ->
            when(status) {
                is MainViewModel.Status.Failure -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.error_to_load_consumption),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is MainViewModel.Status.Success -> {
                    mainViewModel.consumedCounter.value = (status.result as MainViewModel.Result.Counter).value
                    mainViewModel.incrementCounters.value = null
                }
                else -> {}
            }
        }
    }
}