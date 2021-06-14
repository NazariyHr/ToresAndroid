package com.devcraft.tores.utils.extensions

import java.math.BigDecimal

fun BigDecimal.round(numberOfDigitsAfterDot: Int): Double {
    return this.setScale(numberOfDigitsAfterDot, BigDecimal.ROUND_HALF_UP).toDouble()
}