package me.hirotask.loginformcompose.model.domain

import com.google.firebase.Timestamp
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
        "memo" to memo,
        "isComplete" to isComplete
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

fun Map<String, Any>.toTodo(): Todo {
    val id: String = this["id"] as String
    val time: Long = this["time"] as Long
    val content: String = this["content"] as String
    val limitTimestamp: Timestamp = this["limit"] as Timestamp
    val limit: Date = limitTimestamp.toDate()
    val memo: String = this["memo"] as String
    val priority: String = this["priority"] as String
    val isComplete: Boolean = this["isComplete"] as Boolean

    return Todo(
        id = id,
        time = time,
        content = content,
        limit = limit,
        memo = memo,
        priority = priority,
        isComplete = isComplete
    )
}
