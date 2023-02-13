package me.hirotask.loginformcompose.model.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import me.hirotask.loginformcompose.model.domain.Todo

interface FirestoreRepository {
    suspend fun addTodo(
        userUUID: String,
        todo: Todo
    ): Boolean

    suspend fun fetchTodo(
        userUUID: String,
        onSuccess: (List<Todo>) -> Unit = {},
        onFailure: (List<Todo>) -> Unit = {}
    ): Task<QuerySnapshot>

    suspend fun completeTodo(
        userUUID: String,
        id: String,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ): Task<Void>
}