package di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appDeclaration: KoinAppDeclaration = {},
    logEnabled: Boolean
): KoinApplication = startKoin {
    appDeclaration()
    modules(
        platformModule(logEnabled = logEnabled),
        commonModule(),
    )
}