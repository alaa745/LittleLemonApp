package com.alaa.metaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alaa.metaapp.cart.CartDetails
import com.alaa.metaapp.destination.Home
import com.alaa.metaapp.ui.theme.MetaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetaAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Home.route) {
                    composable(Home.route) {
                        HomeScreen(navController)
                    }
                    composable(
                        com.alaa.metaapp.destination.DishDetails.route + "/{${com.alaa.metaapp.destination.DishDetails.argDishId}}",
                        arguments = listOf(navArgument(com.alaa.metaapp.destination.DishDetails.argDishId) {
                            type = NavType.IntType
                        })
                    ) {
                        val id =
                            requireNotNull(it.arguments?.getInt(com.alaa.metaapp.destination.DishDetails.argDishId)) {
                                "Dish id is null"
                            }
                        DishDetails(navController, id)
                    }
                    composable(
                        com.alaa.metaapp.destination.CartDetails.route + "/{${com.alaa.metaapp.destination.DishDetails.argDishId}}",
                        arguments = listOf(navArgument(com.alaa.metaapp.destination.DishDetails.argDishId) {
                            type = NavType.IntType
                        })
                    ) {
                        val id =
                            requireNotNull(it.arguments?.getInt(com.alaa.metaapp.destination.DishDetails.argDishId))
                        CartDetails(navController , id)
                    }
                }
            }
        }
    }
}