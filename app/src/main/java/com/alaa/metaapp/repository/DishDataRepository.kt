package com.alaa.metaapp.repository

import com.alaa.metaapp.database.DatabaseDao
import com.alaa.metaapp.dishdetails.Dish

class DishDataRepository(private val databaseDao: DatabaseDao) {
    val dishes = databaseDao.getAll()

    suspend fun addToCart(dish: Dish){
        databaseDao.insert(dish)
    }

}