package me.hirotask.loginformcompose.model.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.hirotask.loginformcompose.model.util.Todo
import me.hirotask.loginformcompose.model.util.toMap
import me.hirotask.loginformcompose.model.util.toTodo

class FirestoreRepository {
    private val database: FirebaseFirestore get() = FirebaseFirestore.getInstance()


    suspend fun addTodo(userUUID: String, todo: Todo): Boolean = withContext(Dispatchers.IO) {
        try {
            val collection = database.collection("todolist").document("users").collection(userUUID)
            val document = collection.document(todo.id)
            val data = todo.toMap()
            return@withContext document.set(data).isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext false
        }
    }

    suspend fun fetchTodo(
        userUUID: String,
        onSuccess: (List<Todo>) -> Unit = {},
        onFailure: (List<Todo>) -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        val collection = database.collection("todolist").document("users").collection(userUUID)
        collection.limit(20).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess(task.result.map { it.data }.map { it.toTodo() })
            } else {
                onFailure(listOf())
            }
        }
    }


}