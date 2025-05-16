package com.example.poputka.common.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import com.example.poputka.common.data.local.AppDataStoreSourceImpl
import com.example.poputka.common.data.local.db.AppDatabase
import com.example.poputka.common.data.local.db.ioThread
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.common.domain.repository.AppDataStoreSource
import com.example.poputka.common.global_state.AppStateHolderImpl
import com.example.poputka.feature_daily_goal.data.data_source.WeatherApi
import com.example.poputka.feature_notifications.data.util.PrepopulateData.generateConsumptionData
import com.example.poputka.feature_notifications.data.util.PrepopulateData.notifications
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDb(app: Application): AppDatabase {
        lateinit var instance: AppDatabase
        val database = Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(connection: SQLiteConnection) {
                    super.onCreate(connection)
                    ioThread {
                        instance.notificationsDao().upsertNotifications(notifications)
                        instance.consumptionDao().upsertAll(generateConsumptionData())
                    }
                }
            }
        ).fallbackToDestructiveMigration(true)
            .build()
        instance = database
        return instance
    }

    @Provides
    @Singleton
    fun provideAppDataStoreRepository(@ApplicationContext context: Context): AppDataStoreSource =
        AppDataStoreSourceImpl(context)

    @Provides
    @Singleton
    fun provideAppPreferencesStateHolder(appDataStoreSource: AppDataStoreSource): AppStateHolder =
        AppStateHolderImpl(appDataStoreSource)


    private const val TIMEOUT: Long = 30L
    private const val BASE_URL = "https://api.open-meteo.com/"
}