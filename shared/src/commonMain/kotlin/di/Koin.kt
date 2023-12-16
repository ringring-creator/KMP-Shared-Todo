package di

import commonModule
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import platformModule
import ui.todos.TodosViewModel

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication = startKoin {
    appDeclaration()
    modules(
        commonModule(),
        platformModule(),
    )
}

fun KoinApplication.Companion.start(): KoinApplication = initKoin { }

val Koin.TodosViewModel: TodosViewModel get() = get()