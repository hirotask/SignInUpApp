package me.hirotask.loginformcompose.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.model.repository.FirebaseAuthRepositoryImpl
import me.hirotask.loginformcompose.model.domain.UserState
import me.hirotask.loginformcompose.model.repository.FirebaseAuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _userState =
        MutableStateFlow(UserState(isSignIn = FirebaseAuthRepositoryImpl().currentUser != null))
    private val _loading = MutableStateFlow(false)

    val userState  = _userState.asStateFlow()
    val loadingState = _loading.asStateFlow()

    fun signIn(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        _loading.value = true
        viewModelScope.launch {
            firebaseAuthRepository.signIn(
                email,
                password,
                context,
                onSuccess = {
                    _userState.value = UserState(email, password, true)
                    onSuccess()
                },
                onFailure = {
                    _userState.value = UserState(email, password, false)
                    onFailure()
                }
            )
        }
        _loading.value = false
    }

    fun signUp(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        _loading.value = true
        viewModelScope.launch {
            firebaseAuthRepository.signUp(
                email,
                password,
                context,
                onSuccess = {
                    signIn(email, password, context, onSuccess)
                },
                onFailure = {
                    onFailure()
                }
            )
        }
    }

    fun signOut(context: Context) {
        firebaseAuthRepository.signOut(context)
    }

}