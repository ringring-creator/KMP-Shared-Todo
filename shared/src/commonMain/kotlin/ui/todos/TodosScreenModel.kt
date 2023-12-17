package ui.todos

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Todo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class TodosScreenModel(
    private val todoRepository: data.TodoRepository,
) : ScreenModel, KoinComponent {
    private val _todosUiState = MutableStateFlow(
        TodosUiState(todos = emptyList())
    )
    val todosUiState = _todosUiState.asStateFlow()
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    init {
        refresh()
    }

    fun refresh() {
        screenModelScope.launch {
            withContext(dispatcher) {
                updateTodoUiState()
            }
        }
    }

    fun setDone(id: Long, done: Boolean) {
        screenModelScope.launch {
            withContext(dispatcher) {
                todoRepository.updateDone(id, done)
                updateTodoUiState()
            }
        }
    }

    private suspend fun updateTodoUiState() {
        _todosUiState.update {
            TodosUiState(
                todos = todoRepository.list().mapNotNull(Todo::toTodosItemUiState),
            )
        }
    }
}