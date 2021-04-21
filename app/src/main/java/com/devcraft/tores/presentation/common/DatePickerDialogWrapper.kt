package com.devcraft.tores.presentation.common

import android.app.DatePickerDialog
import android.content.Context
import com.devcraft.tores.R
import java.util.*

object DatePickerDialogWrapper {
    fun createWithCurrentDate(
        context: Context,
        callback: (year: Int, month: Int, day: Int) -> Unit
    ): DatePickerDialog {
        val c = Calendar.getInstance()
        return DatePickerDialog(
            context,
            R.style.Theme_Tores_Dialog,
            { v, year, month, day ->
                callback.invoke(year, month, day)
            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        )
    }
}