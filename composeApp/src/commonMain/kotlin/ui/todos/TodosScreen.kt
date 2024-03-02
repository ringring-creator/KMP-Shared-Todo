package ui.todos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kmp_shared_todo.composeapp.generated.resources.Res
import kmp_shared_todo.composeapp.generated.resources.todos_screen_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ui.editTodo.EditTodoScreen

data class TodosUiState(
    val todos: List<TodosItemUiState>,
)

data class TodosItemUiState(
    val id: Long,
    val title: String,
    val done: Boolean,
    val deadline: String,
)

class TodosScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<TodosScreenModel>(factory = { get() })

        val todosUiState by screenModel.todosUiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        TodosScreen(
            uiState = todosUiState,
            setDone = screenModel::setDone,
            onNavigateToEditTodo = { id ->
                navigator.push(EditTodoScreen(id = id))
            }
        )

        DisposableEffect(Unit) {
            screenModel.refresh()

            onDispose { }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun TodosScreen(
        uiState: TodosUiState,
        setDone: (id: Long, done: Boolean) -> Unit,
        onNavigateToEditTodo: (Long?) -> Unit,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(Res.string.todos_screen_title)) }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { onNavigateToEditTodo(null) }) {
                    Icon(
                        Icons.Filled.Create,
                        contentDescription = "create",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        ) {
            TodosContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                todos = uiState.todos,
                onNavigateToEditTodo = onNavigateToEditTodo,
                setDone = setDone,
            )
        }
    }

    @Composable
    private fun TodosContent(
        modifier: Modifier = Modifier,
        todos: List<TodosItemUiState>,
        onNavigateToEditTodo: (Long?) -> Unit,
        setDone: (id: Long, done: Boolean) -> Unit,
    ) {
        LazyColumn(modifier) {
            items(todos) { item ->
                TodosItem(item, onNavigateToEditTodo) {
                    setDone(item.id, it)
                }
            }
        }
    }

    @Composable
    private fun TodosItem(
        item: TodosItemUiState,
        onNavigateToEditTodo: (Long?) -> Unit,
        setDone: (done: Boolean) -> Unit,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onNavigateToEditTodo(item.id) }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = item.done,
                        onCheckedChange = setDone,
                    )
                    Text(
                        item.title,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Deadline",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(item.deadline)
                }
            }
        }
    }
}