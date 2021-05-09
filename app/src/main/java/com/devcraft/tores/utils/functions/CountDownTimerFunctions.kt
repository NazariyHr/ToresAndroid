package com.devcraft.tores.utils.functions

import android.os.CountDownTimer

fun initCountdownTimerWithWithTimeFormattingTick(
    remainingTimeMillis: Long,
    interval: Long,
    onTick: (String) -> Unit,
    onFinish: () -> Unit,
    onTimerInitted: ((CountDownTimer) -> Unit)? = null
) {
    val timer = object : CountDownTimer(remainingTimeMillis, interval) {
        override fun onTick(millisUntilFinished: Long) {
            val seconds = millisUntilFinished / 1000
            val secondsInMinute = 60
            val secondsInHour = secondsInMinute * 60

            val hStr: String
            val mStr: String
            val sStr: String

            var temp: Long = seconds

            if (temp >= secondsInHour) {
                val h = temp / secondsInHour
                temp %= secondsInHour

                hStr = if (h >= 10) {
                    "$h"
                } else {
                    "0$h"
                }

            } else {
                hStr = "00"
            }
            if (temp >= secondsInMinute) {
                val m = temp / secondsInMinute
                temp %= secondsInMinute

                mStr = if (m >= 10) {
                    "$m"
                } else {
                    "0$m"
                }
            } else {
                mStr = "00"
            }

            sStr = if (temp >= 10) {
                "$temp"
            } else {
                "0$temp"
            }

            onTick.invoke("$hStr:$mStr:$sStr")
        }

        override fun onFinish() {
            onFinish.invoke()
        }
    }
    onTimerInitted?.invoke(timer)
    timer.start()
}
