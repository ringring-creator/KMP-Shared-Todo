package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import data.local.db.LocalDb
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.dsl.module

actual fun platformModule(logEnabled: Boolean) = module {
    single<SqlDriver> { createSqliteDriver() }
}.also {
    if (logEnabled) Napier.base(DebugAntilog())
}

private fun createSqliteDriver(): SqlDriver {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    LocalDb.Schema.create(driver)
    return driver
}