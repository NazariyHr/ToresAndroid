package com.devcraft.tores.utils.extensions

import android.widget.EditText

fun EditText.doubleValue(): Double {
    return if (this.text.isEmpty()) 0.0 else this.text.toString().toDouble()
}