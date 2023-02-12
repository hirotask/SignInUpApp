package me.hirotask.loginformcompose.ui

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import com.alorma.compose.settings.ui.SettingsSwitch

@Composable
fun SettingsPage() {
    SettingsSwitch(
        icon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "vibration")},
        title = { Text(text= "バイブレーション機能")},
        subtitle = { Text(text= "ボタンを押したときのバイブレーションを設定します")}
    ) {

    }
}