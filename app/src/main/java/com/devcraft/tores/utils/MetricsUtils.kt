package com.devcraft.tores.utils

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.roundToInt


object MetricsUtils {
    fun dpToPx(ctx: Context, dp: Int): Int {
        val displayMetrics: DisplayMetrics = ctx.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }
}
