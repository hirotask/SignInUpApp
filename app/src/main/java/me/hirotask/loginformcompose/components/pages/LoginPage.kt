package me.hirotask.loginformcompose.components.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.components.atoms.EmailTextField
import me.hirotask.loginformcompose.components.atoms.LoginButton
import me.hirotask.loginformcompose.components.atoms.PasswordTextField
import me.hirotask.loginformcompose.components.atoms.SignInButton
import me.hirotask.loginformcompose.firebase.FirebaseConf

@Composable
fun LoginPage(
    onClickHandler: () -> Unit = {}
) {
    var ajax by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val firebaseConf = FirebaseConf()
    val LoginSubmit = {
        scope.launch(Dispatchers.IO) {
            ajax = true
            //ログイン処理
            firebaseConf.login(email,password,context)
            ajax = false
        }
        Unit
    }
    val SigninSubmit = {
        scope.launch(Dispatchers.IO) {
            ajax = true
            //新規登録処理
            firebaseConf.signup(email, password, context)

            firebaseConf.login(email, password, context)

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
            modifier = Modifier.fillMaxWidth()
        ) { password = it }
        Spacer(Modifier.height(12.dp))
        LoginButton(
            ajax,
            Modifier
                .fillMaxWidth()
                .height(56.dp), LoginSubmit
        )
        Spacer(Modifier.height(6.dp))
        SignInButton(
            ajax,
            Modifier
                .fillMaxWidth()
                .height(56.dp),
            SigninSubmit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginForm() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LoginPage()
    }
}

