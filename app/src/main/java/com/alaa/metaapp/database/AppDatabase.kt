package com.alaa.metaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alaa.metaapp.cart.CartItem
import com.alaa.metaapp.dishdetails.Dish

@Database(entities = [CartItem::class], version = 3, exportSchema = true)
abstract class AppDatabase: RoomDatabase(){
    abstract fun databaseDao(): DatabaseDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "App_Database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}