package me.hirotask.loginformcompose.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun NormalButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    loading : Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick, enabled = !loading, modifier = modifier
    ) {
        if(loading) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(stringResource(id = text))
        }
    }
}