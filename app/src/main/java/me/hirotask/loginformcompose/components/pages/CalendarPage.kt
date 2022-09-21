package me.hirotask.loginformcompose.components.pages

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import me.hirotask.loginformcompose.components.organisms.StaticCalendar

@Composable
fun CalendarPage() {
    Scaffold (
        topBar = {
            TopAppBar (
                title = { Text("カレンダー") },
            )
        },
        content = {
            StaticCalendar()
        }
    )
}