package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import data.local.db.LocalDb
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.dsl.module

actual fun platformModule(logEnabled: Boolean) = module {
    single<SqlDriver> { NativeSqliteDriver(LocalDb.Schema, "Local.db") }
}.also {
    if (logEnabled) Napier.base(DebugAntilog())
}