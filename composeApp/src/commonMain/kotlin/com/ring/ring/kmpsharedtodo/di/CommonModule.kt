package com.ring.ring.kmpsharedtodo.di

import com.ring.ring.kmpsharedtodo.data.TodoRepository
import com.ring.ring.kmpsharedtodo.ui.editTodo.EditTodoScreenModel
import com.ring.ring.kmpsharedtodo.ui.todos.TodosScreenModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun commonModule() = module {
    single<TodoRepository> { TodoRepository(sqlDriver = get()) }
    factory { TodosScreenModel(todoRepository = get()) }
    factory { EditTodoScreenModel(todoRepository = get(), id = it[0]) }
}

expect fun platformModule(logEnabled: Boolean): Module