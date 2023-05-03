package com.alaa.metaapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alaa.metaapp.dishdetails.Dish

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dish: Dish)

    @Query("SELECT * FROM cart")
    fun getAll(): LiveData<List<Dish>>
}