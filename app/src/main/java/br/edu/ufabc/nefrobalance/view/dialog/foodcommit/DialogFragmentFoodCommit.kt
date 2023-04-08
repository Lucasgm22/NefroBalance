package br.edu.ufabc.nefrobalance.view.dialog.foodcommit

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import br.edu.ufabc.nefrobalance.R
import br.edu.ufabc.nefrobalance.databinding.DialogFragmentFoodCommitBinding

class DialogFragmentFoodCommit : DialogFragment() {
    private lateinit var binding: DialogFragmentFoodCommitBinding
    private val args by navArgs<DialogFragmentFoodCommitArgs>()


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
                    val quantity = binding.textInputQuantityConsumed.text.toString().toLong()
                    val message = if (args.isLiquid) getString(R.string.dialog_food_commit_valid_liquid_input)
                    else getString(R.string.dialog_food_commit_valid_food_input)
                    Toast.makeText(
                        requireContext(),
                        message.format(quantity, args.foodName),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: NumberFormatException) {
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


}