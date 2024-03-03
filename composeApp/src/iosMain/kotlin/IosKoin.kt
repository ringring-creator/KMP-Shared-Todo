import di.initKoin
import org.koin.core.Koin
import org.koin.core.KoinApplication
import ui.editTodo.EditTodoScreenModel
import ui.todos.TodosScreenModel

fun KoinApplication.Companion.start(logEnabled: Boolean): KoinApplication = initKoin(
    appDeclaration = { },
    logEnabled = logEnabled
)

val Koin.todosScreenModel: TodosScreenModel
    get() = get()

val Koin.editTodoScreenModel: EditTodoScreenModel
    get() = get()