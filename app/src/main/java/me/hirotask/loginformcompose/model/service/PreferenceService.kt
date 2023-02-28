package me.hirotask.loginformcompose.model.service

import android.content.Context
import me.hirotask.loginformcompose.model.domain.Settings

class PreferenceService(context: Context) {
    fun addPreference(settings: Settings) {

    }

    fun getPreferences(): Settings {
        return Settings(false)
    }
}