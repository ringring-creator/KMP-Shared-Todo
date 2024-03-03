package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import data.local.db.LocalDb
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.dsl.module

actual fun platformModule(logEnabled: Boolean) = module {
    single<SqlDriver> {
        AndroidSqliteDriver(LocalDb.Schema, get(), "Local.db")
    }
}.also {
    if (logEnabled) {
        Napier.base(DebugAntilog())
    }
}