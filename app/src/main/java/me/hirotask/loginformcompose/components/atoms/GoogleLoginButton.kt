package me.hirotask.loginformcompose.components.atoms

import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    submit: () -> Unit,
) {
    Button(
        onClick = submit, enabled = true, modifier = modifier
    ) {
        Text("Googleでログイン")

    }
}