package me.hirotask.loginformcompose.model.firebase

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseAuthConf {

    val currentUser get() = Firebase.auth.currentUser

    suspend fun signin(email: String, password: String, context: Context, onComplete: () -> Unit = {}) =

        withContext(Dispatchers.IO) {
            Firebase.auth.signInWithEmailAndPassword(
                email, password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "ログインに成功しました", Toast.LENGTH_LONG).show()
                    onComplete()
                } else {
                    Toast.makeText(context, "ログインに失敗しました", Toast.LENGTH_LONG).show()
                }
            }
        }


    suspend fun signup(email: String, password: String, context: Context,onComplete: () -> Unit = {}) =

        withContext(Dispatchers.IO) {
            Firebase.auth.createUserWithEmailAndPassword(
                email, password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "新規登録に成功しました", Toast.LENGTH_LONG).show()
                    onComplete()
                } else {
                    Toast.makeText(context, "新規登録に失敗しました", Toast.LENGTH_LONG).show()
                }
            }
        }

    fun signout() {
        Firebase.auth.signOut()
    }

}