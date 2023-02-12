package me.hirotask.loginformcompose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.ui.components.DrawerText
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme
import me.hirotask.loginformcompose.viewmodel.AuthViewModel
import me.hirotask.loginformcompose.viewmodel.TodoViewModel

@Composable
fun TodoPage(
    toLogin: () -> Unit = {},
    toSetting: () -> Unit = {},
    toAdd: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel(),
    todoViewModel: TodoViewModel = viewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val todoList by todoViewModel.list.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerText(icon = Icons.Default.Settings, text = "設定", action = toSetting)
            DrawerText(icon = Icons.Default.Home, text = "ログアウト") {
                authViewModel.signOut()

                scope.launch {
                    scaffoldState.drawerState.apply { if (isOpen) close() }
                    scaffoldState.snackbarHostState.showSnackbar("ログアウトしました")

                    toLogin()
                }
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("TODO一覧") },
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
        floatingActionButton = {
            FloatingActionButton(onClick = { toAdd() }) {
                Icon(Icons.Filled.Add, contentDescription = "add Todo")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Column {
            todoList.forEach {
                Text(it.content)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TodoPagePreview() {
    LoginFormComposeTheme {
        TodoPage()
    }
}