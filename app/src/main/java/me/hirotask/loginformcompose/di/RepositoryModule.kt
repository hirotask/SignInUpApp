package me.hirotask.loginformcompose.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.hirotask.loginformcompose.model.repository.FirebaseAuthRepository
import me.hirotask.loginformcompose.model.repository.FirebaseAuthRepositoryImpl
import me.hirotask.loginformcompose.model.repository.FirestoreRepository
import me.hirotask.loginformcompose.model.repository.FirestoreRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(auth: FirebaseAuth): FirebaseAuthRepository {
        return FirebaseAuthRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository(database: FirebaseFirestore): FirestoreRepository {
        return FirestoreRepositoryImpl(database)
    }
}