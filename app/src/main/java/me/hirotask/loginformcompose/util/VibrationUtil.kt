package me.hirotask.loginformcompose.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

object VibrationUtil {

    fun getVibrator(context: Context): Vibrator {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun Vibrator.doVibrate() {
        val time = 10L

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                this.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                this.vibrate(time)
            }
        } catch (_: Exception) {}
    }

}