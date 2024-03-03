package com.ring.ring.kmpsharedtodo.data

import com.ring.ring.kmpsharedtodo.data.ScreenSettings.Companion.IS_DARK_MODE_KEY
import com.russhwolf.settings.Settings

data class ScreenSettings(
    val isDarkMode: Boolean,
) {
    companion object {
        const val IS_DARK_MODE_KEY = "isDarkMode"
    }
}

class ScreenSettingsRepository(
    private val settings: Settings
) {
    fun get(): ScreenSettings {
        return ScreenSettings(
            isDarkMode = settings.getBoolean(IS_DARK_MODE_KEY, false)
        )
    }

    fun set(screenSettings: ScreenSettings) {
        settings.putBoolean(IS_DARK_MODE_KEY, screenSettings.isDarkMode)
    }
}