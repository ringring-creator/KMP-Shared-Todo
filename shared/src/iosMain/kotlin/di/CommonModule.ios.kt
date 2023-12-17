package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import data.local.db.LocalDb
import org.koin.dsl.module

actual fun platformModule() = module {
    single<SqlDriver> { NativeSqliteDriver(LocalDb.Schema, "Local.db") }
}