package me.hirotask.loginformcompose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.hirotask.loginformcompose.model.domain.Settings
import me.hirotask.loginformcompose.model.service.PreferenceService

class SettingsViewModel(val preference: PreferenceService): ViewModel() {
    private val _settings = MutableStateFlow(
        Settings(
            vibration = false
        )
    )

    val settings = _settings.asStateFlow()
}