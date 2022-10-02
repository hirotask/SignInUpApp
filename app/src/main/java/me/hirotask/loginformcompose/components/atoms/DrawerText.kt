package me.hirotask.loginformcompose.components.atoms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DrawerText(
    icon: ImageVector,
    iconDescription: String = "",
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, iconDescription)
        Text(text, modifier = Modifier.padding(16.dp))
    }
    Divider()
}