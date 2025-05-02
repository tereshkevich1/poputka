package com.example.poputka.feature_notifications.data.di

import android.content.Context
import com.example.poputka.common.data.local.db.AppDatabase
import com.example.poputka.feature_notifications.data.NotificationAlarmScheduler
import com.example.poputka.feature_notifications.data.repository.NotificationsRepository
import com.example.poputka.feature_notifications.data.repository.NotificationsRepositoryImpl
import com.example.poputka.feature_notifications.domain.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideAlarmScheduler(
        @ApplicationContext context: Context,
    ): AlarmScheduler = NotificationAlarmScheduler(context)


    @Provides
    @Singleton
    fun provideNotificationsRepository(
        db: AppDatabase
    ): NotificationsRepository = NotificationsRepositoryImpl(db.notificationsDao())

}


