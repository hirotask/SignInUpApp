package me.hirotask.loginformcompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.model.firebase.FirebaseAuthRepository
import me.hirotask.loginformcompose.model.firebase.FirestoreRepository
import me.hirotask.loginformcompose.model.util.Todo
import me.hirotask.loginformcompose.model.util.getCompleted
import java.util.*

class TodoViewModel: ViewModel() {

    private val _list = MutableStateFlow(listOf<Todo>())
    private val _loading = MutableStateFlow(false)

    val list: StateFlow<List<Todo>> get() = _list
    val loading: StateFlow<Boolean> get() = _loading

    fun addTodo(content: String, priority: String, limit: Date, memo: String): Boolean {
        _loading.value = true
        val todo = Todo.create(content, priority, limit, memo, false)
        val firestore = FirestoreRepository()
        val currentUser = FirebaseAuthRepository().currentUser ?: return false

        val uid = currentUser.uid

        viewModelScope.launch {
            firestore.addTodo(uid, todo)
        }
        _loading.value = false
        return true
    }

    fun completeTodo(todo: Todo) {
        _list.value = _list.value.getCompleted(todo)
    }



}