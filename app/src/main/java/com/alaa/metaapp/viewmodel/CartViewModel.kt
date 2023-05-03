package com.alaa.metaapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.alaa.metaapp.database.AppDatabase
import com.alaa.metaapp.dishdetails.Dish
import com.alaa.metaapp.repository.DishDataRepository
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {
    val dishes: LiveData<List<Dish>>
    private val repository: DishDataRepository

    init {
        val databaseDao = AppDatabase.getInstance(application).databaseDao()
        repository = DishDataRepository(databaseDao)
        dishes = repository.dishes
    }

    fun addToCart(dish: Dish){
        viewModelScope.launch {
            repository.addToCart(dish)
        }
    }
}

class CartViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}