import data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.todos.TodosViewModel

fun commonModule() = module {
    single<TodoRepository> { TodoRepository(sqlDriver = get()) }
    singleOf(::TodosViewModel)
}

expect fun platformModule(): Module