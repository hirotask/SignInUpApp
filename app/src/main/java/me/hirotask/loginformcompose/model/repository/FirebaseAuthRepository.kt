package me.hirotask.loginformcompose.model.repository

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.hirotask.loginformcompose.R
import javax.inject.Inject

interface FirebaseAuthRepository {
    val currentUser: FirebaseUser?

    suspend fun signIn(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ): Task<AuthResult>

    suspend fun signUp(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
    ): Task<AuthResult>

    fun signOut(context: Context)
}

class FirebaseAuthRepositoryImpl @Inject constructor(): FirebaseAuthRepository {

    override val currentUser get() = Firebase.auth.currentUser

    override suspend fun signIn(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
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


    override suspend fun signUp(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) =

        withContext(Dispatchers.IO) {
            Firebase.auth.createUserWithEmailAndPassword(
                email, password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, R.string.signup_success, Toast.LENGTH_LONG).show()
                    onSuccess()
                } else {
                    Toast.makeText(context, R.string.signup_failure, Toast.LENGTH_LONG).show()
                    onFailure()
                }
            }
        }

    override fun signOut(context: Context) {
        Firebase.auth.signOut()
        Toast.makeText(context, R.string.signout_success, Toast.LENGTH_LONG).show()
    }

}
