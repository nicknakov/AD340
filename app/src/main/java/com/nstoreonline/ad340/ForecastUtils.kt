package com.nstoreonline.ad340

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting) : String {
    //return String.format("%.2f°", temp)
    return when (tempDisplaySetting) {
        TempDisplaySetting.Fahrenheit -> String.format("%.2f F°", temp)
        TempDisplaySetting.Celsius -> {
            val temp = (temp - 32f) * (5f / 9f)
            String.format("%.2f C°", temp)
        }
    }
}

fun showTempDisplaySettingsDialog(context: Context,
    tempDisplaySettingManager: TempDisplaySettingManager) {
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose Display Units")
        .setMessage("Choose witch temperature unit to use for temperature display")
        .setPositiveButton("F°") { _, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        // Ful version NO LAMBDA
        .setNeutralButton("C°", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
            }
        })
        .setOnDismissListener {
            Toast.makeText(context, "Setting will take affect on App restart!", Toast.LENGTH_LONG)
                .show()
        }

    dialogBuilder.show()
}
