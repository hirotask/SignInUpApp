package me.hirotask.loginformcompose.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.model.firebase.FirebaseAuthConf
import me.hirotask.loginformcompose.model.util.UserState

class AuthViewModel : ViewModel() {
    private val firebaseAuthConf = FirebaseAuthConf()

    private val _userState = MutableStateFlow(UserState(isSignIn = firebaseAuthConf.currentUser != null))
    val userState = _userState.asStateFlow()

    fun signIn(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        viewModelScope.launch {
            firebaseAuthConf.signin(
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
    }

    fun signUp(
        email: String, password: String, context: Context, onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        viewModelScope.launch {
            firebaseAuthConf.signup(
                email,
                password,
                context,
                onSuccess = {
                    signIn(email, password, context)
                }
            )
        }
    }

    fun signOut() {
        firebaseAuthConf.signout()
    }

}