package data

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import data.local.db.LocalDb
import data.local.db.TodoDataSource
import data.local.db.TodoTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

data class Todo(
    val id: Long?,
    val title: String,
    val description: String,
    val done: Boolean,
    val deadline: LocalDate,
)

class DeadlineAdapter : ColumnAdapter<LocalDate, String> {
    override fun decode(databaseValue: String): LocalDate {
        return databaseValue.toLocalDate()
    }

    override fun encode(value: LocalDate): String {
        return value.toString()
    }

}

class TodoRepository(
    private val sqlDriver: SqlDriver,
) {
    private val todoDataSource by lazy {
        TodoDataSource(
            LocalDb(
                driver = sqlDriver,
                TodoTableAdapter = TodoTable.Adapter(DeadlineAdapter()),
            ).todoQueries
        )
    }

    suspend fun list(): List<Todo> = withContext(Dispatchers.IO) {
        todoDataSource.list()
    }

    suspend fun get(id: Long): Todo = withContext(Dispatchers.IO) {
        todoDataSource.get(id = id)
    }

    suspend fun save(todo: Todo) = withContext(Dispatchers.IO) {
        try {
            todoDataSource.upsert(todo)
        } catch (e: Exception) {
            println(e)
        }
    }

    suspend fun updateDone(id: Long, done: Boolean) = withContext(Dispatchers.IO) {
        todoDataSource.updateDone(id, done)
    }

    suspend fun delete(id: Long) = withContext(Dispatchers.IO) {
        todoDataSource.delete(id = id)
    }
}