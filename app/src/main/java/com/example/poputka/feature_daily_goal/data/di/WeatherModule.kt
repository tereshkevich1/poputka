package com.example.poputka.feature_daily_goal.data.di

import android.content.Context
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.feature_daily_goal.data.DefaultLocationClient
import com.example.poputka.feature_daily_goal.data.data_source.WeatherApi
import com.example.poputka.feature_daily_goal.data.data_source.WeatherDataSource
import com.example.poputka.feature_daily_goal.data.data_source.WeatherDataSourceImpl
import com.example.poputka.feature_daily_goal.data.repository.WeatherRepositoryImpl
import com.example.poputka.feature_daily_goal.domain.LocationClient
import com.example.poputka.feature_daily_goal.domain.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherDataSource: WeatherDataSource,
        appStateHolder: AppStateHolder
    ): WeatherRepository =
        WeatherRepositoryImpl(weatherDataSource, appStateHolder)


    @Provides
    @Singleton
    fun provideWeatherDataSource(api: WeatherApi): WeatherDataSource =
        WeatherDataSourceImpl(api)


    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun provideLocationClient(
        @ApplicationContext context: Context,
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationClient =
        DefaultLocationClient(context, fusedLocationProviderClient)

}