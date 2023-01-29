package me.hirotask.loginformcompose

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.toDate(pattern: String = "yyyy/mm/dd"): Date? {
    val sdFormat = SimpleDateFormat(pattern)
    return sdFormat.parse(this)
}