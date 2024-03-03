package com.ring.ring.kmpsharedtodo.ui.todos

import com.ring.ring.kmpsharedtodo.data.Todo

fun Todo.toTodosItemUiState() = id?.let {
    TodosItemUiState(
        id = it,
        title = title,
        done = done,
        deadline = deadline.toString(),
    )
}