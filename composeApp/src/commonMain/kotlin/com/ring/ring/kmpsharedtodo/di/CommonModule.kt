package com.ring.ring.kmpsharedtodo.di

import com.ring.ring.kmpsharedtodo.data.ScreenSettingsRepository
import com.ring.ring.kmpsharedtodo.data.TodoRepository
import com.ring.ring.kmpsharedtodo.ui.AppModel
import com.ring.ring.kmpsharedtodo.ui.editTodo.EditTodoScreenModel
import com.ring.ring.kmpsharedtodo.ui.todos.TodosScreenModel
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module

fun commonModule() = module {
    single { AppModel(screenSettingsRepository = get()) }
    factory { TodosScreenModel(todoRepository = get()) }
    factory { EditTodoScreenModel(todoRepository = get(), id = it[0]) }
    single { TodoRepository(sqlDriver = get()) }
    single { ScreenSettingsRepository(settings = get()) }
    single { Settings() }
}

expect fun platformModule(logEnabled: Boolean): Module