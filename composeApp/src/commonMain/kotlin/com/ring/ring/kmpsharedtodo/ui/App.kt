package com.ring.ring.kmpsharedtodo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import com.ring.ring.kmpsharedtodo.ui.todos.TodosScreen
import org.koin.compose.getKoin

@Composable
fun App(
    appModel: AppModel = getKoin().get()
) {
    val isDarkMode by appModel.isDarkMode.collectAsState()

    MaterialTheme(
        colors = if (isDarkMode) darkColors() else lightColors()
    ) {
        Navigator(TodosScreen())
    }
}