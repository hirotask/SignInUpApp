package me.hirotask.loginformcompose.data.repository

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.hirotask.loginformcompose.R
import me.hirotask.loginformcompose.domain.repository.FirebaseAuthRepository
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): FirebaseAuthRepository {

    override val currentUser get() = auth.currentUser

    override suspend fun signIn(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) =
        withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(
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
            auth.createUserWithEmailAndPassword(
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
        auth.signOut()
        Toast.makeText(context, R.string.signout_success, Toast.LENGTH_LONG).show()
    }

}
