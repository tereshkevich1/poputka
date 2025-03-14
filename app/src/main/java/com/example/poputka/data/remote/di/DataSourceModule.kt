package com.example.poputka.data.remote.di

import com.example.poputka.data.remote.data_source.AuthenticationDataSource
import com.example.poputka.data.remote.data_source.AuthenticationDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun provideAuthenticationDataSource(authenticationDataSourceImpl: AuthenticationDataSourceImpl): AuthenticationDataSource
}