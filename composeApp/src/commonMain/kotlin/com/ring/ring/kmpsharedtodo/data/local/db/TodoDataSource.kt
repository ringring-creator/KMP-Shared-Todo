package com.ring.ring.kmpsharedtodo.data.local.db

import com.ring.ring.kmpsharedtodo.data.Todo

class TodoDataSource(private val queries: TodoQueries) {
    fun list(): List<Todo> = queries.selectAll().executeAsList().map {
        convert(it)
    }

    fun get(id: Long): Todo = queries.selectById(id).executeAsOne().let {
        convert(it)
    }

    fun upsert(todo: Todo) {
        if (todo.id == null) {
            insert(todo = todo)
        } else {
            update(todo = todo)
        }
    }

    fun updateDone(id: Long, done: Boolean) {
        queries.updateDone(
            done = done,
            id = id,
        )
    }

    fun delete(id: Long) = queries.delete(id)

    private fun insert(todo: Todo) {
        queries.insert(
            title = todo.title,
            description = todo.description,
            done = todo.done,
            deadline = todo.deadline,
        )
    }

    private fun update(todo: Todo) {
        val id = todo.id ?: return
        queries.update(
            id = id,
            title = todo.title,
            description = todo.description,
            done = todo.done,
            deadline = todo.deadline,
        )
    }

    private fun convert(todoTable: TodoTable) = Todo(
        id = todoTable.id,
        title = todoTable.title,
        description = todoTable.description,
        done = todoTable.done,
        deadline = todoTable.deadline,
    )
}