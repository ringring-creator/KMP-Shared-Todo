package com.ring.ring.kmpsharedtodo

import data.Todo
import data.TodoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

fun Route.customerRouting() {
    val todoRepository by KoinJavaComponent.inject<TodoRepository>(TodoRepository::class.java)
    route("/todo") {
        get("/") {
            call.respondText("Hello, World!")
        }
        get("/todos") {
            val todos = todoRepository.list()
            call.respond(todos)
        }
        get("/todos/{id}") {
            val id = call.parameters["id"]?.toLong() ?: return@get
            val todo = todoRepository.get(id)
            call.respond(todo)
        }
        post("/todos") {
            val todo = call.receive<Todo>()
            val newTodo = todoRepository.save(todo)
            call.respond(newTodo)
        }
        put("/todos/{id}") {
            val id = call.parameters["id"]?.toLong() ?: return@put
            val todo = call.receive<Todo>()
            val done = todo.done
            val updatedTodo = todoRepository.updateDone(id, done)
            call.respond(updatedTodo)
        }
        delete("/todos/{id}") {
            val id = call.parameters["id"]?.toLong() ?: return@delete
            todoRepository.delete(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}