package me.hirotask.loginformcompose.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.ui.components.DrawerContent

@Composable
fun SettingsPage(toTodo: () -> Unit, toLogin: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(
                scaffoldState = scaffoldState,
                TodoAction = {
                    toTodo()
                },
                LogoutAction = {
                    toLogin()
                }
            )
        },
        topBar = {
            TopAppBar(
                title = { Text("設定") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        },
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu icon")
                    }

                }
            )
        },
    ) {

    }
}