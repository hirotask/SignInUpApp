package me.hirotask.loginformcompose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.model.SpConf
import me.hirotask.loginformcompose.model.firebase.FirebaseConf
import me.hirotask.loginformcompose.ui.components.EmailTextField
import me.hirotask.loginformcompose.ui.components.NormalButton
import me.hirotask.loginformcompose.ui.components.PasswordTextField

@Composable
fun LoginPage(
    onPreviousHandler: () -> Unit = {},
    onSignInHandler: () -> Unit = {},
) {
    var ajax by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val spConf = SpConf(context)
    val scope = rememberCoroutineScope()
    val firebaseConf = FirebaseConf()
    val LoginSubmit = {
        scope.launch(Dispatchers.IO) {
            ajax = true
            //ログイン処理
            firebaseConf.signin(email, password, context, onComplete = onSignInHandler)

            spConf.saveAccountSession(email, password)

            ajax = false
        }
        Unit
    }
    val SignUpSubmit = {
        scope.launch(Dispatchers.IO) {
            ajax = true
            //新規登録処理
            firebaseConf.signup(email, password, context)

            firebaseConf.signin(email, password, context, onComplete = onSignInHandler)

            ajax = false
        }
        Unit
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Icon(
            painter = rememberVectorPainter(image = Icons.Default.ArrowBack),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp)
                .clickable(onClick = onPreviousHandler)
        )
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
            NormalButton(
                "ログイン",
                ajax,
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                LoginSubmit
            )
            Spacer(Modifier.height(6.dp))
            NormalButton(
                "新規登録",
                ajax,
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                SignUpSubmit
            )
        }
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

