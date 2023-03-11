package me.hirotask.loginformcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.domain.domainobject.Task
import me.hirotask.loginformcompose.domain.domainobject.getCompleted
import me.hirotask.loginformcompose.domain.repository.FirebaseAuthRepository
import me.hirotask.loginformcompose.domain.repository.FirestoreRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
): ViewModel() {

    private val _list: MutableStateFlow<List<Task>> = MutableStateFlow(listOf())
    private val _loading = MutableStateFlow(false)

    val list: StateFlow<List<Task>> = _list.asStateFlow()
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun addTodo(content: String, priority: String, limit: Date, memo: String): Boolean {
        _loading.value = true
        val task = Task.create(content, priority, limit, memo, false)
        val currentUser = firebaseAuthRepository.currentUser ?: return false

        val uid = currentUser.uid

        viewModelScope.launch {
            firestoreRepository.addTodo(uid, task)
        }
        _loading.value = false
        return true
    }

    fun completeTodo(task: Task) {
        viewModelScope.launch {
            firebaseAuthRepository.currentUser?.let { user ->
                val uid = user.uid
                firestoreRepository.completeTodo(uid, task.id, onSuccess = {
                    _list.value = _list.value.getCompleted(task)
                })
            }
        }
    }

    fun fetchTodoList() {
        val currentUser = firebaseAuthRepository.currentUser
        currentUser?.let {
            viewModelScope.launch {
                val uid = currentUser.uid

                firestoreRepository.fetchTodo(
                    uid,
                    onSuccess = {
                        _list.value = it
                    }
                )
            }
        }
    }
}