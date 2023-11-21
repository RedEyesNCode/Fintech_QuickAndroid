package com.redeyesncode.quickpe.ui.dialogs

import android.app.AlertDialog
import android.content.Context

class MultiSelectDialog(context: Context, options: ArrayList<String>, onSelected: (List<String>) -> Unit) {

    private var selectedItems = ArrayList<Int>()

    init {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Options")
        val optionArray = options.toTypedArray()

        builder.setMultiChoiceItems(optionArray, null) { _, which, isChecked ->
            if (isChecked) {
                selectedItems.add(which)
            } else if (selectedItems.contains(which)) {
                selectedItems.remove(which)
            }
        }

        builder.setPositiveButton("OK") { _, _ ->
            val selectedOptions = selectedItems.map { options[it] }
            onSelected(selectedOptions)
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}
