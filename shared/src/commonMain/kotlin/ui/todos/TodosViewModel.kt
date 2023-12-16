package ui.todos

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

class TodosViewModel(
    private val todoRepository: data.TodoRepository,
) : KoinComponent {
    private val _todosUiState = MutableStateFlow(
        TodosUiState(todos = emptyList())
    )
    val todosUiState = _todosUiState.asStateFlow()
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            withContext(dispatcher) {
                updateTodoUiState()
            }
        }
    }

    fun setDone(id: Long, done: Boolean) {
        viewModelScope.launch {
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