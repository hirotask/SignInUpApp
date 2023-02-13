package me.hirotask.loginformcompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.getDeadline
import me.hirotask.loginformcompose.model.util.Todo
import me.hirotask.loginformcompose.ui.components.DrawerContent
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
            DrawerContent(scaffoldState = scaffoldState, SettingsAction = {
                toSetting()
            }, LogoutAction = {
                toLogin()
            })
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
        LazyColumn {
            items(todoList) { todo ->
                CardTodoListItem(todo = todo) {
                    todoViewModel.completeTodo(todo)
                }
            }
        }
    }
}

@Composable
fun CardTodoListItem(todo: Todo, onCompleteTodo: () -> Unit = {}) {
    if (todo.isComplete) return

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = todo.content, style = MaterialTheme.typography.h5)
                Text(text = "優先度:${todo.priority}", style = MaterialTheme.typography.body1)
                Text(text = "期限:${todo.limit.getDeadline()}日後")
            }
            Checkbox(checked = todo.isComplete, onCheckedChange = {
                onCompleteTodo()
            })
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