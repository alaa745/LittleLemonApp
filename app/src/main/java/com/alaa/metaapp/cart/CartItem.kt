package com.alaa.metaapp.cart

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey
    val dishId: Int,
    val dishName: String,
    val dishDescription: String,
    val dishPrice: Double,
    val dishQuantity: Int,
    @DrawableRes val dishImageResource: Int
)
