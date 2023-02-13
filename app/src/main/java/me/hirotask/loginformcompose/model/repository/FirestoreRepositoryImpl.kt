package me.hirotask.loginformcompose.model.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.hirotask.loginformcompose.model.domain.Todo
import me.hirotask.loginformcompose.model.domain.toMap
import me.hirotask.loginformcompose.model.domain.toTodo

class FirestoreRepositoryImpl(
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
): FirestoreRepository {

    companion object {
        const val COLLECTION = "todolist"
        const val DOCUMENT = "users"
    }

    override suspend fun addTodo(userUUID: String, todo: Todo): Boolean = withContext(Dispatchers.IO) {
        try {
            val collection = database.collection(COLLECTION).document(DOCUMENT).collection(userUUID)
            val document = collection.document(todo.id)
            val data = todo.toMap()
            return@withContext document.set(data).isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext false
        }
    }

    override suspend fun fetchTodo(
        userUUID: String,
        onSuccess: (List<Todo>) -> Unit,
        onFailure: (List<Todo>) -> Unit
    ) = withContext(Dispatchers.IO) {
        val collection = database.collection(COLLECTION).document(DOCUMENT).collection(userUUID)
        collection.limit(20).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess(task.result.map { it.data }.map { it.toTodo() })
            } else {
                onFailure(listOf())
            }
        }
    }

    override suspend fun completeTodo(
        userUUID: String,
        id: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) = withContext(Dispatchers.IO) {
        val collection = database.collection(COLLECTION).document(DOCUMENT).collection(userUUID)
        collection.document(id).update("isComplete", true).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

}