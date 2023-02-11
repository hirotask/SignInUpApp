package me.hirotask.loginformcompose.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.hirotask.loginformcompose.util.UserState

class AuthViewModel: ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    private fun signIn(email: String, password: String, context: Context) {
        Firebase.auth.signInWithEmailAndPassword(
            email, password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                _userState.value = UserState(email = email, password = password, isSignIn = true)
                Toast.makeText(context, "ログインに成功しました", Toast.LENGTH_LONG).show()
            } else {
                _userState.value = UserState(email = email, password = password, isSignIn = false)
                Toast.makeText(context, "ログインに失敗しました", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signUp(email: String, password: String, context: Context) {
        Firebase.auth.createUserWithEmailAndPassword(
            email, password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                signIn(email, password, context)
                Toast.makeText(context, "新規登録に成功しました", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "新規登録に失敗しました", Toast.LENGTH_LONG).show()
            }
        }
    }
}