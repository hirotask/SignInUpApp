package me.hirotask.loginformcompose.model.util

import java.util.*

data class Todo(
    val id: String,
    val time: Long,
    val content: String,
    val priority: String,
    val limit: Date,
    val memo: String,
    val isComplete: Boolean
) {
    companion object {
        fun create(content: String, priority: String, limit: Date, memo: String, isComplete: Boolean) =
            Todo(UUID.randomUUID().toString(), Date().time, content, priority, limit, memo, isComplete)
    }

    fun getCompleted(): Todo = this.copy(isComplete = true)

}

fun Todo.toMap(): Map<String, *> {
    return hashMapOf(
        "id" to id,
        "time" to time,
        "content" to content,
        "priority" to priority,
        "limit" to limit,
        "memo" to memo
    )
}

fun List<Todo>.getCompleted(todo: Todo): List<Todo> {
    return this.map {
        if (it.id == todo.id) {
            it.getCompleted()
        } else {
            it
        }
    }
}
