package com.example.poputka.common.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import com.example.poputka.common.data.local.AppDataStoreSourceImpl
import com.example.poputka.common.data.local.db.AppDatabase
import com.example.poputka.common.data.local.db.ioThread
import com.example.poputka.common.domain.repository.AppDataStoreSource
import com.example.poputka.common.global_state.AppStateHolder
import com.example.poputka.common.global_state.AppStateHolderImpl
import com.example.poputka.feature_notifications.data.util.PrepopulateData.notifications
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
                    }
                }
            }
        ).build()
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

}