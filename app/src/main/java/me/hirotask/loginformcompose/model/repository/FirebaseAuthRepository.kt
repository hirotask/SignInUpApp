package me.hirotask.loginformcompose.model.repository

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

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

    fun signOut()
}