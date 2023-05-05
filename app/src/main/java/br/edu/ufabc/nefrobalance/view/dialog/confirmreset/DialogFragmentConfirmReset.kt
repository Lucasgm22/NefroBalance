package br.edu.ufabc.nefrobalance.view.dialog.confirmreset

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import br.edu.ufabc.nefrobalance.R
import br.edu.ufabc.nefrobalance.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DialogFragmentConfirmReset : DialogFragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_confirm_reset_title))
            .setPositiveButton(getString(R.string.button_reset)) { _, _ ->

                parentFragment?.let {
                    mainViewModel.resetCountersInRepository().observe(it.viewLifecycleOwner) { status ->
                        when(status) {
                            is MainViewModel.Status.Failure -> {
                                Toast.makeText(requireContext(),
                                    getString(R.string.failed_to_reset), Toast.LENGTH_SHORT).show()
                            }

                            is MainViewModel.Status.Success -> {
                                mainViewModel.consumedCounter.value = (status.result as MainViewModel.Result.Counter).value
                                Toast.makeText(requireContext(),
                                    getString(R.string.reset_success), Toast.LENGTH_SHORT).show()
                            }

                            else -> {}
                        }
                    }
                } ?: throw Exception("No parent fragment with DialogFragmentConfirmReset")

            }
            .setNegativeButton(getString(R.string.button_cancel)) { _, _ -> }
            .create()
    }
}