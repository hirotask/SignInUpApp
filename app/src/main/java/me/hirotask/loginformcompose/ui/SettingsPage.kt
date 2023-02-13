package me.hirotask.loginformcompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            SectionTitle(title = "アクセシビリティ")
            SwitchPreference("バイブレーション", "ボタン押下時の振動: オン")
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        title,
        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primary,
        modifier = modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
    )
}

@Composable
private fun BasicPreference(
    title: String,
    summary: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
        }
        Column {
            Text(title, fontWeight = FontWeight.Bold)
            Text(summary, color = Color.Gray, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
private fun SwitchPreference(
    title: String,
    summary: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    defaultState: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(summary, color = Color.Gray, style = MaterialTheme.typography.body2)
            }
        }
        Switch(checked = defaultState, onCheckedChange = onCheckedChange)
    }


}