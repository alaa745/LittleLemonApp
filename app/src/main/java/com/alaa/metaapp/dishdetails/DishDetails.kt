package com.alaa.metaapp

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
//import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
//import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.alaa.metaapp.dishdetails.Dish
import com.alaa.metaapp.repository.DishRepository
import com.alaa.metaapp.ui.theme.LittleLemonColor
import com.alaa.metaapp.viewmodel.CartViewModel
import com.alaa.metaapp.viewmodel.CartViewModelFactory
//import com.example.littlelemon.ui.theme.LittleLemonColor
import kotlinx.coroutines.launch

//import java.lang.reflect.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishDetails(navController: NavHostController, id: Int) {
    val context = LocalContext.current
    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(context.applicationContext as Application)
    )

//    val dishes = cartViewModel.dishes.observeAsState(listOf()).value

    val dish = requireNotNull(DishRepository.getDish(id))
//    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val names = listOf("Favourite", "Face", "Email")
    val selectedItem = remember { mutableStateOf(items[0]) }
    var cartItemCount by remember { mutableStateOf(0) }
    var cartItemCount2 by remember { mutableStateOf(0) } // new state // variable


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFF495E57)) {
                Spacer(Modifier.height(40.dp))
                items.forEachIndexed { index,item ->
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.White),
                        icon = { androidx.compose.material3.Icon(item, contentDescription = null) },
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
        Scaffold(
            topBar = {
                androidx.compose.material.TopAppBar(
                    backgroundColor = Color.White
                ) {
                    TopAppBar(
                        drawerState = drawerState,
                        scope = scope,
                        counter = cartItemCount2,
                        navController = navController,
                        id = dish.id
                    )
                }
            }
        ) { padding ->
            Modifier.padding(padding)
            DishDetailsContent(cartViewModel,navController, dish, cartItemCount , { count -> cartItemCount = count }) { count ->
                cartItemCount2 = count
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishDetailsContent(
    cartViewModel: CartViewModel,
    navController: NavHostController,
    dish: Dish,
    cartItemCount: Int,
    incrementCounter: (Int) -> Unit,
    onAddToCart: (Int) -> Unit
){
    val scaffoldState2 = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(scaffoldState2)

    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 130.dp,
//        sheetBackgroundColor = LittleLemonColor.green,
        sheetShape = RoundedCornerShape(topStart = 28.dp , topEnd = 28.dp),

        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Swipe up to expand sheet")
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sheet content")
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        scope.launch { bottomSheetScaffoldState.bottomSheetState.partialExpand() }
                    }
                ) {
                    Text("Click to collapse sheet")
                }
            }
        }) {
        Column {
            Image(painter = painterResource(
                id = dish.imageResource),
                contentDescription = "",
                Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Column(Modifier.padding(start = 8.dp)) {
                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = dish.description , style = MaterialTheme.typography.bodyLarge)
                Counter(dish , incrementCounter)
                AddCartBtn(cartViewModel , navController , dish , cartItemCount , onAddToCart)
            }
        }
    }
}

@Composable
fun AddCartBtn(
    cartViewModel: CartViewModel,
    navController: NavHostController,
    dish: Dish,
    cartItemCount: Int,
    onAddToCart: (Int) -> Unit
){
    Row(modifier = Modifier
        .padding(end = 8.dp)) {
        Button(
            onClick = {
                cartViewModel.addToCart(dish)
                onAddToCart(cartItemCount)
            },
            Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Add to Cart $${dish.price}",
                color = LittleLemonColor.green
            )
        }
    }
}

@Composable
fun Counter(dish: Dish,incrementCounter: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        var counter by remember {
            mutableStateOf(1)
        }
        incrementCounter(counter)
        TextButton(
            onClick = {
                if (counter > 0){
                    counter--
                    incrementCounter(counter)
                }
            },
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(16.dp),
        )
        TextButton(
            onClick = {
                counter++
                incrementCounter(counter)
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
            )
        }
    }
//    AddCartBtn(dish = dish)
}
//
//@Preview
//@Composable
//fun DishDetailsPreview() {
//    DishDetails(id = 1)
//}
