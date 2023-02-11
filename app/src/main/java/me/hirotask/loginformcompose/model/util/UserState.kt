package me.hirotask.loginformcompose.model.util

data class UserState(
    val email: String = "",
    val password: String = "",
    val isSignIn: Boolean = false
)
