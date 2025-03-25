package com.example.poputka.feature_auth.data.repository.di

import com.example.poputka.feature_auth.data.repository.AuthenticationRepositoryImpl
import com.example.poputka.feature_auth.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository
}