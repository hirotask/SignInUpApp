package me.hirotask.loginformcompose.firebase

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class FirebaseConf(
    val auth: FirebaseAuth = Firebase.auth
) {
    suspend fun login(email: String, password: String, context: Context) =
        withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(
                email, password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "ログインに成功しました", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "ログインに失敗しました", Toast.LENGTH_LONG).show()
                }
            }
        }

    suspend fun signup(email: String, password: String, context: Context) =
        withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(
                email, password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "新規登録に成功しました", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "新規登録に失敗しました", Toast.LENGTH_LONG).show()
                }
            }
        }


}