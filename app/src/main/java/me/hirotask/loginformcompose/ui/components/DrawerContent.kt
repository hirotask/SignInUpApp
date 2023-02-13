package me.hirotask.loginformcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
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

@Composable
fun DrawerText(
    icon: ImageVector,
    iconDescription: String = "",
    text: String,
    action: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = action)
    ) {
        Icon(icon, iconDescription)
        Text(text, modifier = Modifier.padding(16.dp))
    }
    Divider()
}