package me.hirotask.loginformcompose.components.atoms

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NormalButton(
    text: String,
    modifier: Modifier = Modifier,
    submit: () -> Unit,
) {
    Button(
        onClick = submit, enabled = true, modifier = modifier
    ) {
        Text(text)
    }
}