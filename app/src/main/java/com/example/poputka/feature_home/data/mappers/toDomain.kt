package com.example.poputka.feature_home.data.mappers

import com.example.poputka.feature_home.data.models.DailyConsumptionDao
import com.example.poputka.feature_home.domain.models.DailyConsumption
import java.time.LocalDate

fun DailyConsumptionDao.toDomain(): DailyConsumption =
    DailyConsumption(
        totalHydrationVolume = totalHydrationVolume,
        date = LocalDate.parse(date)
    )