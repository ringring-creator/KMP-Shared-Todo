package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import data.local.db.LocalDb
import org.koin.dsl.module

actual fun platformModule() = module {
    single<SqlDriver> { createSqliteDriver() }
}

private fun createSqliteDriver(): SqlDriver {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    LocalDb.Schema.create(driver)
    return driver
}