package me.hirotask.loginformcompose.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.model.firebase.FirebaseAuthConf
import me.hirotask.loginformcompose.util.UserState

class AuthViewModel : ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    private val firebaseAuthConf = FirebaseAuthConf()

    init {
        resetUserState()
    }

    fun signIn(email: String, password: String, context: Context) {
        viewModelScope.launch {
            firebaseAuthConf.signin(
                email,
                password,
                context,
                onSuccess = {
                    _userState.value = UserState(email, password, true)
                },
                onFailure = {
                    _userState.value = UserState(email, password, false)
                }
            )
        }
    }

    fun signUp(email: String, password: String, context: Context) {
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

    private fun resetUserState() {
        _userState.value = UserState()
    }
}