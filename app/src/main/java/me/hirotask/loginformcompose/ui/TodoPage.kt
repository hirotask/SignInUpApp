package me.hirotask.loginformcompose.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.firebase.FirebaseConf
import me.hirotask.loginformcompose.ui.components.DrawerText

@Composable
fun TodoPage(
    drawerContent1Action: () -> Unit = {},
    drawerContent2Action: () -> Unit = {},
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerText(icon = Icons.Default.Settings, text = "設定", action = drawerContent1Action)
            DrawerText(icon = Icons.Default.Home, text = "ログアウト") {
                val firebaseConf = FirebaseConf()
                firebaseConf.signout()

                scope.launch {
                    scaffoldState.drawerState.apply { if (isOpen) close() }
                    scaffoldState.snackbarHostState.showSnackbar("ログアウトしました")

                    drawerContent2Action()
                }
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("カレンダー") },
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

@Preview(showSystemUi = true)
@Composable
fun TodoPagePreview() {

}