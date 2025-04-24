package com.example.poputka.core.data.di

import android.content.Context
import com.example.poputka.core.data.local.AppDataStoreSourceImpl
import com.example.poputka.core.domain.repository.AppDataStoreSource
import com.example.poputka.core.domain.repository.AppPreferencesStateHolder
import com.example.poputka.core.global_state.AppPreferencesStateHolderImpl
import com.example.poputka.core.global_state.local_settings_state.PersonalInfoStateHolderImpl
import com.example.poputka.feature_settings.domain.PersonalInfoStateHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDataStoreRepository(@ApplicationContext context: Context): AppDataStoreSource =
        AppDataStoreSourceImpl(context)

    @Provides
    @Singleton
    fun provideAppPreferencesStateHolder(appDataStoreSource: AppDataStoreSource): AppPreferencesStateHolder =
        AppPreferencesStateHolderImpl(appDataStoreSource)

    @Provides
    @Singleton
    fun providePersonalInfoStateHolder(appDataStoreSource: AppDataStoreSource): PersonalInfoStateHolder =
        PersonalInfoStateHolderImpl(appDataStoreSource)
}