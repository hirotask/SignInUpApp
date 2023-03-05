package me.hirotask.loginformcompose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.hirotask.loginformcompose.model.repository.FirebaseAuthRepository
import me.hirotask.loginformcompose.model.repository.FirebaseAuthRepositoryImpl
import me.hirotask.loginformcompose.model.repository.FirestoreRepository
import me.hirotask.loginformcompose.model.repository.FirestoreRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {
    @Binds
    @Singleton
    abstract fun bindToAuthRepository(impl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository

    @Binds
    @Singleton
    abstract fun bindToFirestoreRepository(impl: FirestoreRepositoryImpl): FirestoreRepository
}