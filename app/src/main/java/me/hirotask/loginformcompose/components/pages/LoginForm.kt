package me.hirotask.loginformcompose.components.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.components.atoms.EmailTextField
import me.hirotask.loginformcompose.components.atoms.LoginButton
import me.hirotask.loginformcompose.components.atoms.PasswordTextField

@Composable
fun LoginForm() {
    var ajax by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val submit = {
        scope.launch(Dispatchers.IO) {
            ajax = true
            //ログイン処理
            ajax = false
        }
        Unit
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailTextField(value = email, modifier = Modifier.fillMaxWidth()) { email = it }
        Spacer(Modifier.height(4.dp))
        PasswordTextField(
            value = password,
            modifier = Modifier.fillMaxWidth(),
            { password = it },
            submit
        )
        Spacer(Modifier.height(12.dp))
        LoginButton(
            ajax,
            Modifier
                .fillMaxWidth()
                .height(56.dp), submit
        )
    }
}

@Preview
@Composable
fun PreviewLoginForm() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LoginForm()
    }
}

