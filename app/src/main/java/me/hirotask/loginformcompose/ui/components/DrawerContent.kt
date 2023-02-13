package me.hirotask.loginformcompose.ui.components

import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.viewmodel.AuthViewModel

@Composable
fun DrawerContent(
    scaffoldState: ScaffoldState,
    authViewModel: AuthViewModel = viewModel(),
    TodoAction: () -> Unit = {},
    SettingsAction: () -> Unit = {},
    LogoutAction: () -> Unit = {}
) {

    val scope = rememberCoroutineScope()

    DrawerText(icon = Icons.Default.Home, text = "Todo一覧", action = {
        TodoAction()
    })
    DrawerText(icon = Icons.Default.Settings, text = "設定", action = {
        SettingsAction()
    })
    DrawerText(icon = Icons.Default.Home, text = "ログアウト") {
        authViewModel.signOut()

        scope.launch {
            scaffoldState.drawerState.apply { if (isOpen) close() }
            scaffoldState.snackbarHostState.showSnackbar("ログアウトしました")

            LogoutAction()
        }
    }
}