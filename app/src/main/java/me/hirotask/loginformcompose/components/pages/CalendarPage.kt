package me.hirotask.loginformcompose.components.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.components.atoms.DrawerText
import me.hirotask.loginformcompose.components.organisms.StaticCalendar

@Composable
fun CalendarPage() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold (
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerText(icon = Icons.Default.Settings,text = "設定")
            DrawerText(icon = Icons.Default.Home, text = "ログアウト")
        },
        topBar = {
            TopAppBar (
                title = { Text("カレンダー") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.apply {
                                if(isClosed) open() else close()
                            }
                        }
                    },
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu icon")
                    }

                }
            )
        },
        content = {
            StaticCalendar()
        }
    )
}