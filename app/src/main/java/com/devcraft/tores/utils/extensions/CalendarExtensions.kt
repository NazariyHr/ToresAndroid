package com.devcraft.tores.utils.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun SimpleDateFormat.formatYearMonthDay(
    year: Int,
    month: Int,
    day: Int,
    pattern: String? = null
): String {
    if (pattern != null) {
        this.applyPattern(pattern)
    }
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, day)
    return this.format(Date(calendar.timeInMillis))
}