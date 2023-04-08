package br.edu.ufabc.nefrobalance.view.dialog.confirmreset

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import br.edu.ufabc.nefrobalance.R

class DialogFragmentConfirmReset : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_confirm_reset_title))
            .setPositiveButton(getString(R.string.button_reset)) { _, _ ->
                Toast.makeText(requireContext(), getString(R.string.button_reset), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.button_cancel)) { _, _ -> }
            .create()
    }
}