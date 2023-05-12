package com.alaa.metaapp.repository

import android.util.Log
import com.alaa.metaapp.cart.CartItem
import com.alaa.metaapp.database.CartDao

class DishDataRepository(private val cartDao: CartDao) {
    val cartItems = cartDao.getAll()
    var exist: Boolean? = false
    var quantity: Int? = 0

    suspend fun addToCart(cartItem: CartItem){
        cartDao.insert(cartItem)
    }

    suspend fun updateCart(cartItem: CartItem){
        cartDao.update(cartItem)
    }

    suspend fun getQuantity(cartItem: CartItem): Int?{
        quantity = cartDao.getQuantity(cartItem.dishId)
        Log.d("quant1" , "quant1: ${quantity}")
        Log.d("quant2" , "id: ${cartItem.dishId}")

        return quantity
    }


    suspend fun updateCart2(id: Int , quantity: Int?){
        cartDao.updateProduct(id , quantity)
    }

    suspend fun isExist(cartItem: CartItem): Boolean?{
        exist = cartDao.isExist(cartItem.dishId)
        return exist
    }

}