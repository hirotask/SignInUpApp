package me.hirotask.loginformcompose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.hirotask.loginformcompose.model.domain.Settings
import me.hirotask.loginformcompose.model.service.VibrationUtil

class SettingsViewModel(val vibrationUtil: VibrationUtil): ViewModel() {
    private val _settings = MutableStateFlow(
        Settings(
            vibration = false
        )
    )

    val settings = _settings.asStateFlow()
}