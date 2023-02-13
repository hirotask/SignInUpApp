package me.hirotask.loginformcompose.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.R
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
    val context = LocalContext.current

    DrawerText(icon = Icons.Default.Home, text = R.string.todo_list, action = {
        TodoAction()
    })
    DrawerText(icon = Icons.Default.Settings, text = R.string.settings, action = {
        SettingsAction()
    })
    DrawerText(icon = Icons.Default.Home, text = R.string.sign_out) {
        authViewModel.signOut(context = context)

        scope.launch {
            scaffoldState.drawerState.apply { if (isOpen) close() }

            LogoutAction()
        }
    }
}

@Composable
fun DrawerText(
    icon: ImageVector,
    @StringRes text: Int,
    action: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = action)
            .fillMaxWidth()
    ) {
        Icon(icon, null)
        Text(stringResource(text), modifier = Modifier.padding(16.dp))
    }
    Divider()
}