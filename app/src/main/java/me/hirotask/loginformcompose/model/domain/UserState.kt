package me.hirotask.loginformcompose.model.domain

data class UserState(
    val email: String = "",
    val password: String = "",
    val isSignIn: Boolean = false
)
