package com.alaa.metaapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alaa.metaapp.cart.CartItem

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Query("SELECT EXISTS(SELECT * FROM cart WHERE dishId = :productid)")
    suspend fun isExist(productid: Int?): Boolean?
    @Query("SELECT dishQuantity FROM cart WHERE dishId = :productid")
    suspend fun getQuantity(productid: Int): Int?
    @Query("UPDATE cart SET dishQuantity = :productQuantity WHERE dishId = :productId")
    suspend fun updateProduct(productId: Int , productQuantity: Int?)
    @Query("SELECT * FROM cart")
    fun getAll(): LiveData<List<CartItem>>
}