package com.alaa.metaapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.alaa.metaapp.cart.CartItem
import com.alaa.metaapp.database.AppDatabase
import com.alaa.metaapp.dishdetails.Dish
import com.alaa.metaapp.repository.DishDataRepository
import com.alaa.metaapp.repository.DishRepository.dishes
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {
//    private val _dishes = MutableLiveData<List<Dish>>()
    var cartItems: LiveData<List<CartItem>>
//    var exist: LiveData<Boolean>?
    private val _exist = MutableLiveData<Boolean>(false)
    val exist: LiveData<Boolean> = _exist

    private val _dishesSize = MutableLiveData<Int>()
    val dishesSize: LiveData<Int> = _dishesSize
    private val repository: DishDataRepository

    init {
        val databaseDao = AppDatabase.getInstance(application).databaseDao()
        repository = DishDataRepository(databaseDao)
        cartItems = repository.cartItems
        cartItems.observeForever {
            _dishesSize.value = it.size
        }
    }

    fun addToCart(cartItem: CartItem){
        viewModelScope.launch {
            repository.addToCart(cartItem)
        }
    }

    fun updateCart(cartItem: CartItem){
        viewModelScope.launch {
            repository.updateCart(cartItem)
        }
    }

    fun isExist(cartItem: CartItem){
        viewModelScope.launch {
            val bool = repository.isExist(cartItem)
            _exist.value = bool
            if (_exist.value == false) {
                addToCart(cartItem)
                Log.d("exist" , "exist: ${_exist.value}")
            }
            else{

                updateCart(cartItem)
                Log.d("exist" , "exist: ${_exist.value}")

            }
            Log.d("bool" , "bool ${bool}")
            Log.d("booll" , "booll ${_exist.value}")

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