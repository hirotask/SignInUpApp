package me.hirotask.loginformcompose.ui.components

import android.widget.CalendarView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun StaticCalendar() {
    var date by remember { mutableStateOf("") }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { CalendarView(it) },
        update = {
            it.setOnDateChangeListener { calendarView, year, month, day ->
                date = "$day - ${month + 1} - $year"
            }
        }
    )

}