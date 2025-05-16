package com.example.poputka.feature_home.data.di

import com.example.poputka.common.data.local.db.AppDatabase
import com.example.poputka.feature_home.data.repository.ConsumptionRepositoryImpl
import com.example.poputka.feature_home.domain.repository.ConsumptionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideConsumption(db: AppDatabase): ConsumptionRepository =
        ConsumptionRepositoryImpl(db.consumptionDao())
}