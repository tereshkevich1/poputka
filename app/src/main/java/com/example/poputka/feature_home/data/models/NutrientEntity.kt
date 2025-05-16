package com.example.poputka.feature_home.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "nutrients", foreignKeys = [ForeignKey(
    entity = DrinkEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("drink_id"),
    onDelete = ForeignKey.CASCADE
)])
data class NutrientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "drink_id")
    val drinkId: Int,
    @ColumnInfo(name = "nutrient_name")
    val nutrientName: String,
    @ColumnInfo(name = "amount_per_100_ml")
    val amountPer100ml: Float
)



