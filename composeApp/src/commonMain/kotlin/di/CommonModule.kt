package di

import data.TodoRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.editTodo.EditTodoScreenModel
import ui.todos.TodosScreenModel

fun commonModule() = module {
    single<TodoRepository> { TodoRepository(sqlDriver = get()) }
    factory { TodosScreenModel(todoRepository = get()) }
    factory { EditTodoScreenModel(todoRepository = get(), id = it[0]) }
}

expect fun platformModule(logEnabled: Boolean): Module