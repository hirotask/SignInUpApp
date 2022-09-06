package me.hirotask.loginformcompose.components.atoms

import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginButton (
    ajax : Boolean,
    modifier: Modifier = Modifier,
    submit: () -> Unit,
) {
    Button(
        onClick = submit, enabled = !ajax, modifier = modifier
    ) {
        if(ajax) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text("ログイン")
        }
    }
}