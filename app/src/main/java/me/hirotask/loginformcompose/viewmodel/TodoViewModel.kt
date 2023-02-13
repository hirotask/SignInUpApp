package me.hirotask.loginformcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.model.repository.FirebaseAuthRepositoryImpl
import me.hirotask.loginformcompose.model.repository.FirestoreRepositoryImpl
import me.hirotask.loginformcompose.model.domain.Todo
import me.hirotask.loginformcompose.model.domain.getCompleted
import java.util.*

class TodoViewModel : ViewModel() {

    private val _list: MutableStateFlow<List<Todo>> = MutableStateFlow(listOf())
    private val _loading = MutableStateFlow(false)

    val list: StateFlow<List<Todo>> = _list.asStateFlow()
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    init {
        fetchTodoList()
    }

    fun addTodo(content: String, priority: String, limit: Date, memo: String): Boolean {
        _loading.value = true
        val todo = Todo.create(content, priority, limit, memo, false)
        val firestore = FirestoreRepositoryImpl()
        val auth = FirebaseAuthRepositoryImpl()
        val currentUser = auth.currentUser ?: return false

        val uid = currentUser.uid

        viewModelScope.launch {
            firestore.addTodo(uid, todo)
        }
        _loading.value = false
        return true
    }

    fun completeTodo(todo: Todo) {
        val firestore = FirestoreRepositoryImpl()
        val auth = FirebaseAuthRepositoryImpl()

        viewModelScope.launch {
            auth.currentUser?.let { user ->
                val uid = user.uid
                firestore.completeTodo(uid, todo.id, onSuccess = {
                    _list.value = _list.value.getCompleted(todo)
                })
            }
        }
    }

    private fun fetchTodoList() {
        val auth = FirebaseAuthRepositoryImpl()
        val firestore = FirestoreRepositoryImpl()

        val currentUser = auth.currentUser
        currentUser?.let {
            viewModelScope.launch {
                val uid = currentUser.uid

                firestore.fetchTodo(
                    uid,
                    onSuccess = {
                        _list.value = it
                    }
                )
            }
        }
    }
}