package com.alaa.metaapp.destination

interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route = "Home"
}

object DishDetails : Destinations {
    override val route = "Menu"
    const val argDishId = "dishId"
}

object CartDetails : Destinations {
    override val route = "Cart"
    const val argDishId = "dishId"

}
