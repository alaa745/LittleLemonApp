package com.alaa.metaapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alaa.metaapp.cart.CartItem
import com.alaa.metaapp.database.AppDatabase
import com.alaa.metaapp.repository.DishDataRepository
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {
//    private val _dishes = MutableLiveData<List<Dish>>()
    var cartItems: LiveData<List<CartItem>>
//    var exist: LiveData<Boolean>?
    private val _exist = MutableLiveData<Boolean>(false)
    val exist: LiveData<Boolean> = _exist

    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> = _quantity

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
            val databaseQuantity = repository.getQuantity(cartItem)
            val currentQuant = cartItem.dishQuantity
            _quantity.value = databaseQuantity
            Log.d("quant2" , "quant2: ${_quantity.value}")

            repository.updateCart2(cartItem.dishId , databaseQuantity!!+currentQuant)
        }
    }

    fun isExist(cartItem: CartItem){
        viewModelScope.launch {
            val bool = repository.isExist(cartItem)
            _exist.value = bool
            if (_exist.value == false) {
                addToCart(cartItem)
            }
            else{
                updateCart(cartItem)
            }
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