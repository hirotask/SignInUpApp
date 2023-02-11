package me.hirotask.loginformcompose.util

data class UserState(
    val email: String = "",
    val password: String = "",
    val isSignIn: Boolean = false
)
