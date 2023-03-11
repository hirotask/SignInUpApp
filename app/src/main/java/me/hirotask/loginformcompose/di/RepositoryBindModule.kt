package me.hirotask.loginformcompose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.hirotask.loginformcompose.data.repository.FirebaseAuthRepositoryImpl
import me.hirotask.loginformcompose.data.repository.FirestoreRepositoryImpl
import me.hirotask.loginformcompose.domain.repository.FirebaseAuthRepository
import me.hirotask.loginformcompose.domain.repository.FirestoreRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBindModule {

    @Binds
    @Singleton
    fun bindsFirebaseAuthRepository(impl: FirebaseAuthRepositoryImpl) : FirebaseAuthRepository

    @Binds
    @Singleton
    fun bindsFirestoreRepository(impl: FirestoreRepositoryImpl): FirestoreRepository
}