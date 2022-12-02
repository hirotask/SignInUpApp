package me.hirotask.loginformcompose.ui

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme
import me.hirotask.loginformcompose.util.Priority
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun TodoAddPage(
    toTodo: () -> Unit = {},
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current

    var title by remember { mutableStateOf("") }
    var memo by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var priority by remember { mutableStateOf(Priority.HIGHEST.jpPriority) }
    var date by remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, myear: Int, mmonth: Int, mday:Int ->
            date = "$myear/$mmonth/$mday"
        },
        year,
        month,
        day
    )


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("TODO追加ページ") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            toTodo()
                        },
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back icon")
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Todo内容")
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
            )

            Spacer(Modifier.height(4.dp))

            Text("優先度")
            Column {
                OutlinedTextField(
                    value = priority,
                    onValueChange = { priority = it },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            Icons.Filled.ArrowDropDown,
                            "",
                            Modifier.clickable { dropdownExpanded = !dropdownExpanded },
                        )
                    }
                )
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                ) {
                    Priority.values().forEach {
                        DropdownMenuItem(
                            onClick = {
                                dropdownExpanded = false
                                priority = it.jpPriority
                            },
                        ) {
                            val isSelected = it.jpPriority == priority
                            val style = if (isSelected) {
                                MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.secondary
                                )
                            } else {
                                MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                            Text(text = it.jpPriority, style = style)
                        }
                    }
                }
            }
            Spacer(Modifier.height(4.dp))

            Text("終了期限")
            Column {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            Icons.Filled.DateRange,
                            "",
                            Modifier.clickable { datePickerDialog.show() },
                        )
                    }
                )

            }

            Spacer(Modifier.height(4.dp))

            Text("メモ")
            OutlinedTextField(
                value = memo,
                onValueChange = { memo = it },
                modifier = Modifier.fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
            )

            Spacer(Modifier.height(4.dp))

            Button(onClick = { /*TODO*/ }) {
                Text("追加する")
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Preview(showSystemUi = true)
@Composable
fun TodoAddPagePreview() {
    LoginFormComposeTheme {
        TodoAddPage()
    }
}