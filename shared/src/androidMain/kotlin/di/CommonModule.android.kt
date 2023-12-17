package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import data.local.db.LocalDb
import org.koin.dsl.module

actual fun platformModule() = module {
    single<SqlDriver> {
        AndroidSqliteDriver(LocalDb.Schema, get(), "Local.db")
    }
}