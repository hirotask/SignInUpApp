package me.hirotask.loginformcompose.model.util

import java.util.*

data class Todo(
    val id: String,
    val time: Long,
    val content: String,
    val priority: String,
    val limit: Date,
    val memo: String
) {
    companion object {
        fun create(content: String, priority: String, limit: Date, memo: String) =
            Todo(UUID.randomUUID().toString(), Date().time, content, priority, limit, memo)
    }
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
