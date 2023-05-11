package com.alaa.metaapp.cart

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.alaa.metaapp.R
import com.alaa.metaapp.ui.theme.LittleLemonColor
import com.alaa.metaapp.viewmodel.CartViewModel
import com.alaa.metaapp.viewmodel.CartViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetails(navController: NavHostController, id: Int) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(context.applicationContext as Application)
    )

    val cartItems = cartViewModel.cartItems.observeAsState(listOf()).value
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val names = listOf("Favourite", "Face", "Email")
    val selectedItem = remember { mutableStateOf(items[0]) } // new state variable

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
            CartScreen(cartItems)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartItems: List<CartItem>){
    val coroutineScope = rememberCoroutineScope()

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
                CartBottomSheetContent(coroutineScope , bottomSheetScaffoldState)
            }
        }
    ){
        AllContents(cartItems)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBottomSheetContent(
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
){
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
            onClick = {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.partialExpand()
                }
            },
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
@Composable
fun AllContents(cartItems: List<CartItem>) {
    LazyColumn(
        Modifier
            .fillMaxHeight()
            .padding(10.dp)
//                .background(Color.Green),
//            verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(cartItems) { cartItem ->
            CartContents(cartItem)
        }
        item {
            PaymentSummary(cartItems)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContents(cartItem: CartItem) {
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
                    Text(text = cartItem.dishName, style = MaterialTheme.typography.displayMedium)
                    Text(
                        text = cartItem.dishDescription,
                        Modifier.fillMaxWidth(.75f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${cartItem.dishPrice}",
                        Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Row {
                        Text(
                            text = "Quantity: ",
                            Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${cartItem.dishQuantity}",
                            Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Image(
                    painter = painterResource(id = cartItem.dishImageResource),
                    contentDescription = "",
                    modifier = Modifier.clip(shape = RoundedCornerShape(10.dp))
                )
            }
        }
    }
}

@Composable
fun PaymentSummary(cartItems: List<CartItem>){
    var basketTotal = remember {
        mutableStateOf(0.0)
    }
    var basketQuantity = 0
    var total = 0.0
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
            for (cartItem in cartItems){
                basketTotal.value = cartItem.dishPrice * cartItem.dishQuantity
                basketQuantity = cartItem.dishQuantity
                total = basketTotal.value
            }
            Text(text = "Payment summary", Modifier.padding(8.dp) ,style = MaterialTheme.typography.displayMedium)
            Row(Modifier.fillMaxWidth()) {
                Text(text = "Basket total",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.80f) ,style = MaterialTheme.typography.bodyMedium)
                Text(text = "EGP ${basketTotal.value}" ,style = MaterialTheme.typography.bodyMedium)

            }
            Row(Modifier.fillMaxWidth()) {
                Text(text = "Delivery fee",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.80f) ,style = MaterialTheme.typography.bodyMedium)
                Text(text = "EGP 20" ,style = MaterialTheme.typography.bodyMedium)

            }
            Row(Modifier.fillMaxWidth()) {
                Text(text = "Service fee",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.80f) ,style = MaterialTheme.typography.bodyMedium)
                Text(text = "EGP 20",style = MaterialTheme.typography.bodyMedium)

            }
        }
    }
}