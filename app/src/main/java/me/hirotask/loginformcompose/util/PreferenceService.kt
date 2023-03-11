package me.hirotask.loginformcompose.util

import android.content.Context
import me.hirotask.loginformcompose.domain.domainobject.Settings

class PreferenceService(context: Context) {
    fun addPreference(settings: Settings) {

    }

    fun getPreferences(): Settings {
        return Settings(false)
    }
}