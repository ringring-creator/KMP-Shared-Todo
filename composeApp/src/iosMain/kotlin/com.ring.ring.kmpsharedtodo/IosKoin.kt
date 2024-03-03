package com.ring.ring.kmpsharedtodo

import com.ring.ring.kmpsharedtodo.di.initKoin
import com.ring.ring.kmpsharedtodo.ui.editTodo.EditTodoScreenModel
import com.ring.ring.kmpsharedtodo.ui.todos.TodosScreenModel
import org.koin.core.Koin
import org.koin.core.KoinApplication

fun KoinApplication.Companion.start(logEnabled: Boolean): KoinApplication = initKoin(
    appDeclaration = { },
    logEnabled = logEnabled
)

val Koin.todosScreenModel: TodosScreenModel
    get() = get()

val Koin.editTodoScreenModel: EditTodoScreenModel
    get() = get()