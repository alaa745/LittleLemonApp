package com.alaa.metaapp.cart

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.alaa.metaapp.repository.DishRepository
import com.alaa.metaapp.R
import com.alaa.metaapp.dishdetails.Dish
import com.alaa.metaapp.ui.theme.LittleLemonColor
import com.alaa.metaapp.viewmodel.CartViewModel
import com.alaa.metaapp.viewmodel.CartViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetails(navController: NavHostController, id: Int) {
    val scaffoldState = rememberScaffoldState()
    val dish = requireNotNull(DishRepository.getDish(id))
    val context = LocalContext.current
    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(context.applicationContext as Application)
    )

    val dishes = cartViewModel.dishes.observeAsState(listOf()).value

    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Gray,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
                        contentDescription = "Little Lemon Logo",
                        modifier = Modifier
                            .fillMaxWidth(0.5F)

                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "",

                        )
                    }
                }
            )
        }
    ) { padding ->
        Modifier.padding(padding)
        val scaffoldState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded,
//            skipHiddenState = false
        )
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(scaffoldState)
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 130.dp,
            sheetContent = {
                Column {
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Basket total",
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth(.80f) ,style = MaterialTheme.typography.bodyMedium)
                        Text(text = "EGP20",  Modifier.padding(8.dp) ,style = MaterialTheme.typography.bodyMedium)

                    }
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Delivery fee",
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth(.80f) ,style = MaterialTheme.typography.bodyMedium)
                        Text(text = "EGP20",  Modifier.padding(8.dp) ,style = MaterialTheme.typography.bodyMedium)

                    }
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Delivery fee",
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth(.80f) ,style = MaterialTheme.typography.bodyMedium)
                        Text(text = "EGP20",  Modifier.padding(8.dp) ,style = MaterialTheme.typography.bodyMedium)

                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)) {
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColor.yellow)
                        ) {
                            Text(
                                text = "CLOSE",
                                color = LittleLemonColor.green
                            )
                        }
                    }
                }
            }
        ){
            AllContents(dishes)
        }
    }
}

@Composable
fun AllContents(dishes: List<Dish>) {
    LazyColumn(
        Modifier
            .fillMaxHeight()
            .padding(10.dp)
//                .background(Color.Green),
//            verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(dishes) {dish ->
            CartContents(dish)
        }
        item {
            PaymentSummary()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContents(dish: Dish) {
    Column {
        Card(
            onClick = { /*TODO*/ },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
//                    Modifier.padding(8.dp)
                ) {
                    Text(text = dish.name, style = MaterialTheme.typography.displayMedium)
                    Text(
                        text = dish.description,
                        Modifier.fillMaxWidth(.75f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${dish.price}",
                        Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.bodySmall
                    )

                }
                Image(
                    painter = painterResource(id = dish.imageResource),
                    contentDescription = "",
                    modifier = Modifier.clip(shape = RoundedCornerShape(10.dp))
                )
            }
        }
    }
}

@Composable
fun PaymentSummary(){
    Card(
        Modifier.padding(top = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            Modifier
                .padding(8.dp)
//                .background(Color.Red)
                .fillMaxWidth(),
        ) {
            Text(text = "Payment summary", Modifier.padding(8.dp) ,style = MaterialTheme.typography.displayMedium)
            Row(Modifier.fillMaxWidth()) {
                Text(text = "Basket total",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.80f) ,style = MaterialTheme.typography.bodyMedium)
                Text(text = "EGP20",  Modifier.padding(8.dp) ,style = MaterialTheme.typography.bodyMedium)

            }
            Row(Modifier.fillMaxWidth()) {
                Text(text = "Delivery fee",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.80f) ,style = MaterialTheme.typography.bodyMedium)
                Text(text = "EGP20",  Modifier.padding(8.dp) ,style = MaterialTheme.typography.bodyMedium)

            }
            Row(Modifier.fillMaxWidth()) {
                Text(text = "Service fee",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.80f) ,style = MaterialTheme.typography.bodyMedium)
                Text(text = "EGP20",  Modifier.padding(8.dp) ,style = MaterialTheme.typography.bodyMedium)

            }
        }
    }
}