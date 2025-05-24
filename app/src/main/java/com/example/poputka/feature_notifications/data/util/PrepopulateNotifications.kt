package com.example.poputka.feature_notifications.data.util

import com.example.poputka.R
import com.example.poputka.common.presentation.drinkCategories
import com.example.poputka.feature_home.data.models.ConsumptionEntity
import com.example.poputka.feature_notifications.data.models.NotificationEntity

object PrepopulateData {
    val notifications = listOf(
        NotificationEntity(
            id = 1,
            titleResId = R.string.notification_morning_water,
            time = localTimeToMillis(8, 0),
            isEnabled = true
        ),
        NotificationEntity(
            id = 2,
            titleResId = R.string.notification_after_breakfast,
            time = localTimeToMillis(8, 20),
            isEnabled = true
        ),
        NotificationEntity(
            id = 3,
            titleResId = R.string.notification_lunch_water,
            time = localTimeToMillis(9, 30),
            isEnabled = true
        ),
        NotificationEntity(
            id = 4,
            titleResId = R.string.notification_afternoon_tea,
            time = localTimeToMillis(11, 0),
            isEnabled = false
        ),
        NotificationEntity(
            id = 5,
            titleResId = R.string.notification_evening_water,
            time = localTimeToMillis(13, 0),
            isEnabled = true
        ),
        NotificationEntity(
            id = 6,
            titleResId = R.string.notification_dinner,
            time = localTimeToMillis(18, 0),
            isEnabled = true
        ),
        NotificationEntity(
            id = 7,
            titleResId = R.string.notification_night_relax,
            time = localTimeToMillis(20, 0),
            isEnabled = false
        ),
        NotificationEntity(
            id = 8,
            titleResId = R.string.notification_before_sleep,
            time = localTimeToMillis(22, 30),
            isEnabled = true
        )
    )


    fun generateConsumptionData(): List<ConsumptionEntity> {
        val now = System.currentTimeMillis()
        val oneDayMillis = 24 * 60 * 60 * 1000L
        val random = java.util.Random()

        val allEntries = mutableListOf<ConsumptionEntity>()

        for (dayOffset in 0..30) {
            val dayStart = getStartOfDayMillis(now - dayOffset * oneDayMillis)

            repeat(10) { i ->
                val randomTimeOffset = random.nextInt(24 * 60) * 60 * 1000L
                val timestamp = dayStart + randomTimeOffset
                val volume = listOf(0, 0, 150, 200, 250, 300).random()
                val drinkType = drinkCategories.random()

                if (volume != 0) {
                    allEntries += ConsumptionEntity(
                        drinkType = drinkType.name,
                        volume = volume,
                        hydrationVolume = volume * drinkType.hydration / 100,
                        timestamp = timestamp
                    )
                }
            }
        }

        return allEntries
    }

    private fun getStartOfDayMillis(timestamp: Long): Long {
        val zone = java.time.ZoneId.systemDefault()
        val localDate = java.time.Instant.ofEpochMilli(timestamp).atZone(zone).toLocalDate()
        return localDate.atStartOfDay(zone).toInstant().toEpochMilli()
    }

    private fun localTimeToMillis(hour: Int, minute: Int): Long {
        return (hour * 60 + minute) * 60 * 1000L
    }
}

