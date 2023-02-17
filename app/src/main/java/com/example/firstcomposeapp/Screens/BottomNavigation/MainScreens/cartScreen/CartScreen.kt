package com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.cartScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.firstcomposeapp.NammaGroceryDB
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.apiService.roomDataBase.CartTable
import com.example.firstcomposeapp.components.AppHeader
import com.example.firstcomposeapp.ui.theme.PrimaryGreen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CartScreen(navController: NavHostController) {

    val dataBase = NammaGroceryDB.getInstance()

    val cartData = remember { mutableStateOf(emptyList<CartTable>()) }

    LaunchedEffect(key1 = cartData) {
        cartData.value = NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
    }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxHeight(0.93f),
    ) {
        AppHeader(
            Header = "My Cart",
            LeftImg = null,
            RightImg = null,
            LeftonClick = { /*TODO*/ },
            RightonClick = {}
        )
        if (cartData.value.isEmpty()) {
            val composition by rememberLottieComposition(LottieCompositionSpec.Asset("empty-cart.json"))
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = composition,
                    iterations = 1,
                    modifier = Modifier
                        .height(230.dp)
                )
                Text(
                    text = "There is no item in the Cart",
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Once you add item to the cart, it will appear here",
                    fontFamily = FontFamily(Font(R.font.font_medium)),
                    fontSize = 12.sp,
                    color = Color.Gray.copy(alpha = 0.5f)
                )
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier.fillMaxSize(1F),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            items(cartData.value) { data ->
                CartCardItem(data,
                    increment = {
                        if (dataBase != null) {
                            dataBase.cartDao()?.incrementCount(data.id)
                            dataBase.cartDao()?.setPrice(data.id)
                            GlobalScope.launch {
                                cartData.value = NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                            }
                        }
                    },
                    decrement = {
                        if (dataBase != null) {
                            dataBase.cartDao()?.decrementCount(data.id)
                            dataBase.cartDao()?.setPrice(data.id)
                            GlobalScope.launch {
                                if (data.count == 1) {
                                    val item = CartTable(
                                        data.id,
                                        data.category,
                                        data.count,
                                        data.image,
                                        data.price,
                                        0.0,
                                        data.title,
                                        data.description,
                                        data.rating
                                    )
                                    dataBase.cartDao()?.delete(item)
                                }
                                cartData.value = NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                            }
                        }
                    },
                    remove = {
                        if (dataBase != null) {
                            val item = CartTable(
                                data.id,
                                data.category,
                                data.count,
                                data.image,
                                data.price,
                                0.0,
                                data.title,
                                data.description,
                                data.rating
                            )
                            dataBase.cartDao()?.delete(item)
                            GlobalScope.launch {
                                cartData.value = NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                            }
                        }
                    }
                )
            }
        }
        Divider(
            thickness = 0.5.dp,
            color = Color.Gray.copy(alpha = 0.5f),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 15.dp)
                .align(alignment = Alignment.CenterHorizontally),
        )
    }
}

@Composable
fun CartCardItem(
    data: CartTable,
    increment: () -> Unit,
    decrement: () -> Unit,
    remove: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(data.image),
            null,
            modifier = Modifier
                .height(75.dp)
                .width(75.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 20.dp, top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    data.title?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.font_bold))
                        )
                    }
                    Text(
                        text = "1kg, Price",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.font_bold)),
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                }
                IconButton(onClick = remove) {
                    Image(
                        painterResource(id = R.drawable.remove),
                        null,
                        modifier = Modifier
                            .height(18.dp)
                            .width(18.dp)
                    )
                }
            }
            Row {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(start = 10.dp, top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = decrement) {
                            Image(painterResource(id = R.drawable.minus),
                                null,
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(12.dp))
                        }
                        Text(text = data.count.toString(),
                            fontFamily = FontFamily(Font(R.font.font_bold)),
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .border(
                                    BorderStroke(color = Color.Gray, width = 0.5.dp),
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                        )
                        IconButton(onClick = increment) {
                            Icon(Icons.Outlined.Add, null, tint = PrimaryGreen)
                        }
                    }
                }
                if (data.finalPrice == 0.0) {
                    data.price?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.font_bold)),
                            modifier = Modifier
                                .padding(top = 12.dp )
                        )
                    }
                } else {
                    Text(
                        text = "$" + data.finalPrice.toString(),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.font_bold)),
                        modifier = Modifier
                            .padding(top = 12.dp)
                    )
                }
            }
        }
    }
}
