package me.hirotask.loginformcompose.domain.domainobject

data class UserState(
    val email: String = "",
    val password: String = "",
    val isSignIn: Boolean = false
)
