import data.TodoRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.editTodo.EditTodoScreenModel
import ui.todos.TodosScreenModel

fun commonModule() = module {
    single<TodoRepository> { TodoRepository(sqlDriver = get()) }
    singleOf(::TodosScreenModel)
    single { EditTodoScreenModel(todoRepository = get(), id = it[0]) }
}

expect fun platformModule(): Module