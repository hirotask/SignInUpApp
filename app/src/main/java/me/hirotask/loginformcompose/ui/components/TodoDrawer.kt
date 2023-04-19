package me.hirotask.loginformcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.Destinations
import me.hirotask.loginformcompose.TodoNavigationActions
import me.hirotask.loginformcompose.R
import me.hirotask.loginformcompose.ui.viewmodel.AuthViewModel

@Composable
fun AppModalDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: TodoNavigationActions,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    authViewModel: AuthViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToTodo = {
                    navigationActions.navigateToTodo()
                },
                signOutAction = {
                    navigationActions.navigateToLogin()
                    authViewModel.signOut(context)
                },
                closeDrawer = {
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {
        content()
    }
}

@Composable
private fun AppDrawer(
    currentRoute: String,
    navigateToTodo: () -> Unit,
    signOutAction: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        DrawerHeader()
        DrawerTextButton(
            painter = rememberVectorPainter(image = Icons.Default.Home),
            label = stringResource(id = R.string.todo_list),
            isSelected = currentRoute == Destinations.Todo.destination,
            action = {
                navigateToTodo()
                closeDrawer()
            }
        )
        DrawerTextButton(
            painter = rememberVectorPainter(image = Icons.Default.Settings),
            label = stringResource(
                id = R.string.settings
            ),
            isSelected = currentRoute == Destinations.Settings.destination,
            action = {
//              Setting画面がまだ未実装なため
//                navigateToSettings()
                closeDrawer()
            }
        )
        DrawerTextButton(
            painter = rememberVectorPainter(image = Icons.Default.AccountCircle),
            label = stringResource(
                id = R.string.sign_out
            ),
            isSelected = currentRoute == Destinations.Login.destination,
            action = {
                signOutAction()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun DrawerHeader(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(dimensionResource(id = R.dimen.header_height))
            .padding(dimensionResource(id = R.dimen.header_padding))
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_no_fill),
            contentDescription = stringResource(id = R.string.tasks_header_image_content_description),
            modifier = Modifier.width(dimensionResource(id = R.dimen.header_image_width))
        )
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colors.surface
        )
    }
}

@Composable
private fun DrawerTextButton(
    painter: Painter,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tintColor = if (isSelected) {
        MaterialTheme.colors.secondary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }

    TextButton(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin)),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = tintColor
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, style = MaterialTheme.typography.body2, color = tintColor)
        }
    }
}