package me.hirotask.loginformcompose.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.model.SpConf
import me.hirotask.loginformcompose.model.firebase.FirebaseConf
import me.hirotask.loginformcompose.ui.components.DrawerText
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme

@Composable
fun TodoPage(
    toLogin: () -> Unit = {},
    toSetting: () -> Unit = {},
    toAdd: () -> Unit = {}
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerText(icon = Icons.Default.Settings, text = "設定", action = toSetting)
            DrawerText(icon = Icons.Default.Home, text = "ログアウト") {
                val firebaseConf = FirebaseConf()
                val spConf = SpConf(context)
                firebaseConf.signout()
                spConf.deleteAccountSession()


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

    }
}

@Preview(showSystemUi = true)
@Composable
fun TodoPagePreview() {
    LoginFormComposeTheme {
        TodoPage()
    }
}