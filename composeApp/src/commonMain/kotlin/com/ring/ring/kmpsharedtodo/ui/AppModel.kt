package com.ring.ring.kmpsharedtodo.ui

import cafe.adriel.voyager.core.model.ScreenModel
import com.ring.ring.kmpsharedtodo.data.ScreenSettings
import com.ring.ring.kmpsharedtodo.data.ScreenSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class AppModel(
    private val screenSettingsRepository: ScreenSettingsRepository,
) : ScreenModel, KoinComponent {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    init {
        updateIsDarkMode()
    }

    fun setDarkMode(isDarkMode: Boolean) {
        saveScreenSettings(isDarkMode)
        updateIsDarkMode()
    }

    private fun updateIsDarkMode() {
        _isDarkMode.value = screenSettingsRepository.get().isDarkMode
    }

    private fun saveScreenSettings(isDarkMode: Boolean) {
        val screenSettings = ScreenSettings(isDarkMode)
        screenSettingsRepository.set(screenSettings)
    }
}