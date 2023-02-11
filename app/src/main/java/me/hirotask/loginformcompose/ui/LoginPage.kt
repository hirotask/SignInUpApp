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
import androidx.lifecycle.viewmodel.compose.viewModel
import me.hirotask.loginformcompose.ui.components.EmailTextField
import me.hirotask.loginformcompose.ui.components.NormalButton
import me.hirotask.loginformcompose.ui.components.PasswordTextField
import me.hirotask.loginformcompose.viewmodel.AuthViewModel

@Composable
fun LoginPage(
    onPreviousHandler: () -> Unit = {},
    onSignInHandler: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel()
) {
    var ajax by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val userState by authViewModel.userState.collectAsState()
    val context = LocalContext.current

    val LoginSubmit = {
        authViewModel.signIn(email, password, context)

        if(userState.isSignIn) {
            onSignInHandler()
        }
    }
    val SignUpSubmit = {
        authViewModel.signUp(email, password, context)

        if(userState.isSignIn) {
            onSignInHandler()
        }
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

