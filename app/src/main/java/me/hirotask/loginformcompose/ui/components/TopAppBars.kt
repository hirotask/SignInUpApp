package me.hirotask.loginformcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.hirotask.loginformcompose.R
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme

@Composable
fun TasksTopAppBar(
    openDrawer: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name))},
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun TasksTopAppBarPreview() {
    LoginFormComposeTheme {
        Surface {
            TasksTopAppBar {}
        }
    }
}
