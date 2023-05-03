package com.alaa.metaapp.dishdetails

import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Dish(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val imageResource: Int
)
