package com.alaa.metaapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alaa.metaapp.cart.CartItem

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Query("SELECT EXISTS(SELECT * FROM cart WHERE dishId = :productid)")
    suspend fun isExist(productid: Int?): Boolean?
    @Query("SELECT * FROM cart")
    fun getAll(): LiveData<List<CartItem>>
}