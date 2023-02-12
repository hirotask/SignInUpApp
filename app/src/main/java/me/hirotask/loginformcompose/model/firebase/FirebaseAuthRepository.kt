package me.hirotask.loginformcompose.model.firebase

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.hirotask.loginformcompose.R

class FirebaseAuthRepository {

    val currentUser get() = Firebase.auth.currentUser

    suspend fun signin(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) =
        withContext(Dispatchers.IO) {
            Firebase.auth.signInWithEmailAndPassword(
                email, password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, R.string.signin_success, Toast.LENGTH_LONG).show()
                    onSuccess()
                } else {
                    Toast.makeText(context, R.string.signin_failure, Toast.LENGTH_LONG).show()
                    onFailure()
                }
            }
        }


    suspend fun signup(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
    ) =

        withContext(Dispatchers.IO) {
            Firebase.auth.createUserWithEmailAndPassword(
                email, password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, R.string.signup_success, Toast.LENGTH_LONG).show()
                    onSuccess()
                } else {
                    Toast.makeText(context, R.string.signin_failure, Toast.LENGTH_LONG).show()
                    onFailure()
                }
            }
        }

    fun signout() {
        Firebase.auth.signOut()
    }

}