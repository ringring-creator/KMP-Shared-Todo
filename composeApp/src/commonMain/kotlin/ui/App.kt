package ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.todos.TodosScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(TodosScreen())
    }
}