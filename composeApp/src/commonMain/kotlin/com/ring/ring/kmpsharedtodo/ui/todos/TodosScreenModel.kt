package com.ring.ring.kmpsharedtodo.ui.todos

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.ring.ring.kmpsharedtodo.data.Todo
import com.ring.ring.kmpsharedtodo.data.TodoRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class TodosScreenModel(
    private val todoRepository: TodoRepository,
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
        Napier.d(tag = "TodosScreenModel", message = "refresh")
        screenModelScope.launch {
            withContext(dispatcher) {
                updateTodoUiState()
            }
        }
    }

    fun setDone(id: Long, done: Boolean) {
        Napier.d(tag = "TodosScreenModel", message = "setDone, id: $id, done: $done")
        screenModelScope.launch {
            withContext(dispatcher) {
                todoRepository.updateDone(id, done)
                updateTodoUiState()
            }
        }
    }

    private suspend fun updateTodoUiState() {
        _todosUiState.value = TodosUiState(
            todos = todoRepository.list().mapNotNull(Todo::toTodosItemUiState),
        )
    }
}