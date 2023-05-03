package com.alaa.metaapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alaa.metaapp.repository.DishRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val names = listOf("Favourite", "Face", "Email")
    val selectedItem = remember { mutableStateOf(items[0]) }
    var cartItemCount by remember { mutableStateOf(0) }
    var cartItemCount2 by remember { mutableStateOf(0) } // new state variable

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFF495E57)) {
                Spacer(Modifier.height(40.dp))
                items.forEachIndexed { index,item ->
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.White),
                        icon = { Icon(item, contentDescription = null) },
                        label = {
                            Text(names[index])
                        },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    ) {
        androidx.compose.material.Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                androidx.compose.material.TopAppBar(
                    backgroundColor = Color.White
                ) {
                    TopAppBar(
                        drawerState = drawerState,
                        scope = scope,
                        counter = cartItemCount2
                    )
                }
            }
        ) { padding ->
            Modifier.padding(padding)
            HomeScreenContent(navController)
        }
    }
}

@Composable
fun HomeScreenContent(navController: NavHostController) {
    Column {
//        TopAppBar(
////            color = Color.White
//        )
        UpperPanel()
        LowerPanel(navController, DishRepository.dishes)
    }
}
