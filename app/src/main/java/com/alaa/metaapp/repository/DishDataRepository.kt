package com.alaa.metaapp.repository

import com.alaa.metaapp.cart.CartItem
import com.alaa.metaapp.database.CartDao

class DishDataRepository(private val cartDao: CartDao) {
    val cartItems = cartDao.getAll()
    var exist: Boolean? = false

    suspend fun addToCart(cartItem: CartItem){
        cartDao.insert(cartItem)
    }

    suspend fun updateCart(cartItem: CartItem){
        cartDao.update(cartItem)
    }

    suspend fun isExist(cartItem: CartItem): Boolean?{
        exist = cartDao.isExist(cartItem.dishId)
        return exist
    }

}