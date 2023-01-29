package me.hirotask.loginformcompose.model.firebase

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.hirotask.loginformcompose.util.Todo
import me.hirotask.loginformcompose.util.toMap

class FirestoreConf {
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

//    suspend fun fetchTodo(userUUID: String, limit: Long): List<Todo> = withContext(Dispatchers.IO) {
//        try {
//            val collection = database.collection("todolist").document("users").collection(userUUID)
//            val documents = collection.limit(limit).get().addOnCompleteListener {
//                it.
//            }
//            return documents
//        }
//    }


}