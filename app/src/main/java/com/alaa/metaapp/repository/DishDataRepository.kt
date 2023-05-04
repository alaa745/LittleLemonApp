package com.alaa.metaapp.repository

import android.util.Log
import com.alaa.metaapp.cart.CartItem
import com.alaa.metaapp.database.DatabaseDao

class DishDataRepository(private val databaseDao: DatabaseDao) {
    val cartItems = databaseDao.getAll()
    var exist: Boolean? = false

    suspend fun addToCart(cartItem: CartItem){
        databaseDao.insert(cartItem)
    }

    suspend fun updateCart(cartItem: CartItem){
        databaseDao.update(cartItem)
    }

    suspend fun isExist(cartItem: CartItem): Boolean?{
        exist = databaseDao.isExist(cartItem.dishId)
        return exist
    }

}