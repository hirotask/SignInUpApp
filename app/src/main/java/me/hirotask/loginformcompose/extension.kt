package me.hirotask.loginformcompose

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.toDate(pattern: String = "yyyy/MM/dd"): Date? {
    val sdFormat = SimpleDateFormat(pattern)
    return sdFormat.parse(this)
}

fun Date.getDeadline(): Int  {
    val calendar = Calendar.getInstance()
    calendar.time = this

    val timeMills = calendar.timeInMillis
    val currentTimeMills = System.currentTimeMillis()

    var diff = timeMills - currentTimeMills
    diff /= 1000
    diff /= 60
    diff /= 60
    diff /= 24

    return diff.toInt()
}