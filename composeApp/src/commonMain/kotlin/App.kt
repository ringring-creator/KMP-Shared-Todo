import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.todos.TodosScreen

@Composable
fun App() {
    MaterialTheme {
        TodosScreen()
    }
}