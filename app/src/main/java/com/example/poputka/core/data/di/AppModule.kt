package com.example.poputka.core.data.di

import android.content.Context
import com.example.poputka.core.data.local.AppDataStoreSourceImpl
import com.example.poputka.core.domain.repository.AppDataStoreSource
import com.example.poputka.core.domain.repository.AppPreferencesStateHolder
import com.example.poputka.core.global_state.AppPreferencesStateHolderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppDataStoreRepository(@ApplicationContext context: Context): AppDataStoreSource =
        AppDataStoreSourceImpl(context)

    @Provides
    fun provideAppPreferencesStateHolder(appDataStoreSource: AppDataStoreSource): AppPreferencesStateHolder =
        AppPreferencesStateHolderImpl(appDataStoreSource)
}