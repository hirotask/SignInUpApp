package me.hirotask.loginformcompose.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import me.hirotask.loginformcompose.domain.domainobject.Task as TodoTask

interface FirestoreRepository {
    suspend fun addTodo(
        userUUID: String,
        task: TodoTask
    ): Boolean

    suspend fun fetchTodo(
        userUUID: String,
        onSuccess: (List<TodoTask>) -> Unit = {},
        onFailure: (List<TodoTask>) -> Unit = {}
    ): Task<QuerySnapshot>

    suspend fun completeTodo(
        userUUID: String,
        id: String,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ): Task<Void>
}