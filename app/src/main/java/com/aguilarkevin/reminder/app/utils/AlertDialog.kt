package com.aguilarkevin.reminder.app.utils

import android.content.Context
import com.aguilarkevin.reminder.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlertDialog {

    fun newAlertDialog(context: Context, title: String, message: String) {
        MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}