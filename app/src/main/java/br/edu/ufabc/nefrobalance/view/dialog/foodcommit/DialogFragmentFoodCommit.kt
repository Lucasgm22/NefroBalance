package br.edu.ufabc.nefrobalance.view.dialog.foodcommit

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import br.edu.ufabc.nefrobalance.R
import br.edu.ufabc.nefrobalance.databinding.DialogFragmentFoodCommitBinding
import br.edu.ufabc.nefrobalance.model.entity.ConsumedCounter
import br.edu.ufabc.nefrobalance.model.repository.room.entity.FoodRoom
import br.edu.ufabc.nefrobalance.viewmodel.MainViewModel

class DialogFragmentFoodCommit : DialogFragment() {
    private lateinit var binding: DialogFragmentFoodCommitBinding
    private val args by navArgs<DialogFragmentFoodCommitArgs>()
    private val mainViewModel by activityViewModels<MainViewModel>()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentFoodCommitBinding.inflate(layoutInflater)

        binding.textInputQuantityConsumed.hint =
            if (args.isLiquid) getString(R.string.text_input_quantity_consumed_liquid_hint)
            else getString(R.string.text_input_quantity_consumed_food_hint)

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.dialog_food_commit_title))
            .setMessage(getString(R.string.dialog_commit_food_message_default, args.foodName))
            .setPositiveButton(getString(R.string.button_add)) { _, _ ->
                try {
                    val quantity = binding.textInputQuantityConsumed.text.toString().toDouble()
                    val message = if (args.isLiquid) getString(R.string.dialog_food_commit_valid_liquid_input)
                    else getString(R.string.dialog_food_commit_valid_food_input)
                    var increment: ConsumedCounter?
                    if (args.foodId == -1L) {
                        mainViewModel.incrementCounters.value = ConsumedCounter(
                            quantity,0.0, 0.0, 0.0
                        )
                    } else {
                        parentFragment?.let {
                            mainViewModel.getFoodById(args.foodId).observe(it.viewLifecycleOwner) { status ->
                                when (status) {
                                    is MainViewModel.Status.Failure -> throw Exception("Could no obtain food with id ${args.foodId}")
                                    is MainViewModel.Status.Success -> {
                                        val food = (status.result as MainViewModel.Result.FoodResult).value
                                        increment = getIncrementByFoodAndQuantity(food, quantity)
                                        mainViewModel.incrementCounters.value = if (mainViewModel.incrementCounters.value == null) {
                                            increment
                                        } else {
                                            increment?.let { _ ->
                                                mainViewModel.incrementCounters.value!!.incrementCounters(
                                                    increment!!
                                                )
                                            } ?: throw Exception("Increment was not set")
                                        }
                                    }
                                    else -> {}
                                }
                            }
                        } ?: throw Exception("No parent fragment with DialogFragmentFoodCommit")
                    }

                    Toast.makeText(
                        requireContext(),
                        message.format(quantity, args.foodName),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.dialog_food_commit_invalid_input),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .setNegativeButton(getString(R.string.button_cancel)) { _, _ -> }
            .create()
    }

    private fun getIncrementByFoodAndQuantity(food: FoodRoom, quantity: Double): ConsumedCounter {
        val qntProportion = if (food.isLiquid) {
            food.density?.let { quantity.times(it).div(100.0) } ?: throw Exception("Food id ${food.id} is Liquid without density")
        } else {
            quantity.div(100.0)
        }
        val liquid = if (food.isLiquid) quantity else qntProportion.times(food.moisture)

        return ConsumedCounter(
            liquid = liquid,
            phosphor = qntProportion.times(food.phosphor),
            sodium = qntProportion.times(food.sodium),
            potassium = qntProportion.times(food.potassium)
        )

    }
}