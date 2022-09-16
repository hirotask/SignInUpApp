package me.hirotask.loginformcompose.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

data class FirebaseConf(
    val auth: FirebaseAuth = Firebase.auth
) {
    fun initialize() {

    }
}