package me.hirotask.loginformcompose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.hirotask.loginformcompose.domain.domainobject.Task
import me.hirotask.loginformcompose.getDeadline
import me.hirotask.loginformcompose.ui.components.TasksTopAppBar
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme
import me.hirotask.loginformcompose.ui.viewmodel.TodoViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoScreen(
    toAdd: () -> Unit = {},
    openDrawer: () -> Unit,
    todoViewModel: TodoViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val todoList by todoViewModel.list.collectAsState()
    todoViewModel.fetchTodoList()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TasksTopAppBar(
                openDrawer = openDrawer
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
                CardTodoListItem(task = todo) {
                    todoViewModel.completeTodo(todo)
                }
            }
        }
    }
}

@Composable
fun CardTodoListItem(task: Task, onCompleteTodo: () -> Unit = {}) {
    if (task.isComplete) return

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .shadow(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = task.content, style = MaterialTheme.typography.h5)
                Text(text = "優先度:${task.priority}", style = MaterialTheme.typography.body1)
                Text(text = "期限:${task.limit.getDeadline()}日後")
            }
            Checkbox(checked = task.isComplete, onCheckedChange = {
                onCompleteTodo()
            })
        }
    }

}


@Preview(showSystemUi = true)
@Composable
fun TodoPagePreview() {
    LoginFormComposeTheme {
        TodoScreen({}, {})
    }
}