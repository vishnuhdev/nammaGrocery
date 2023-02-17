@file:Suppress("NAME_SHADOWING", "OPT_IN_IS_NOT_ENABLED")

package com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.firstcomposeapp.NammaGroceryDB
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.apiService.ProductDataItem
import com.example.firstcomposeapp.apiService.roomDataBase.CartTable
import com.example.firstcomposeapp.apiService.roomDataBase.FavoriteTable
import com.example.firstcomposeapp.components.AppHeader
import com.example.firstcomposeapp.components.CustomButton
import com.example.firstcomposeapp.navigation.DetailsScreen
import com.example.firstcomposeapp.ui.theme.PrimaryGreen
import com.example.firstcomposeapp.ui.theme.RatingColor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun DetailsScreen(navController: NavController) {
    //FireBase
    val database = Firebase.database.reference
    val userId = Firebase.auth

    //Room Data Base ==> Favorites
    val favorData = remember { mutableStateOf(emptyList<FavoriteTable>()) }


    val dataBase = NammaGroceryDB.getInstance()
    val context = LocalContext.current

    //Room Data Base ==> Cart
    val cartData = remember { mutableStateOf(emptyList<CartTable>()) }

    val scrollState = rememberScrollState()
    val showMore = rememberSaveable {
        mutableStateOf(false)
    }
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<ProductDataItem>(DetailsScreen.DetailArgs.ProductData)
    val favMatch = favorData.value.any { it.id == data?.id }
    val isFavorite = remember { mutableStateOf(favMatch) }
    val cartMatch = cartData.value.any { it.id == data?.id }
    val isInCart = remember { mutableStateOf(cartMatch) }
    val ratingCount = data?.rating?.toFloatOrNull()?.toInt() ?: 0

    LaunchedEffect(key1 = isFavorite, favorData, isInCart, block = {
        favorData.value = NammaGroceryDB.getInstance()?.favoriteDao()?.getAll()!!
        cartData.value = NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
        val favMatch = favorData.value.any { it.id == data?.id }
        val cartMatch = cartData.value.any { it.id == data?.id }
        isFavorite.value = favMatch
        isInCart.value = cartMatch
    })

    Column(modifier = Modifier
        .fillMaxHeight(0.89f)
        .verticalScroll(state = scrollState)
        .background(color = Color.White)) {
        AppHeader(Header = null,
            LeftImg = R.drawable.back_ios_new_24,
            RightImg = null,
            LeftonClick = { navController.popBackStack() },
            RightonClick = {})
        if (data != null) {
            Image(painter = rememberAsyncImagePainter(data.image),
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .width(330.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth())
        }
        Spacer(modifier = Modifier.height(25.dp))
        if (data != null) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.98f)) {
                Column {
                    Text(text = data.title.toString(),
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.font_black)),
                        modifier = Modifier.padding(start = 15.dp))
                    Text(text = "1Kg,Price",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.font_medium)),
                        modifier = Modifier.padding(start = 15.dp, top = 5.dp),
                        color = Color.Gray.copy(alpha = 0.5f))
                }
                if (!isFavorite.value) {
                    IconButton(onClick = {
                        val item = FavoriteTable(data.id.toString(),
                            data.category,
                            data.image,
                            data.price,
                            data.title,
                            data.description,
                            data.rating)
                        if (dataBase != null) {
                            val namesRef = database.child("FavoriteList")
                            val key = data.id

                            if (key != null) {
                                namesRef.addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        if (!dataSnapshot.hasChild(key)) {
                                            userId.uid?.let {
                                                namesRef.child(it).child(key).setValue(item)
                                            }
                                            dataBase.favoriteDao()?.insert(item)
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.i("Err", error.toString())
                                    }
                                })
                            }
                        }
                        Toast.makeText(context,
                            "${data.title} is Successfully Added to Favorite!!",
                            Toast.LENGTH_SHORT).show()
                        isFavorite.value = !isFavorite.value
                    }) {
                        Icon(Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier
                                .height(28.dp)
                                .width(28.dp))
                    }
                } else {
                    IconButton(onClick = {
                        val items = data.id?.let {
                            FavoriteTable(it,
                                data.category,
                                data.image,
                                data.price,
                                data.title,
                                data.description,
                                data.rating)
                        }
                        if (items != null) {
                            dataBase?.favoriteDao()?.delete(items)
                            userId.uid?.let {
                                database.child("FavoriteList").child(it).child(data.id)
                                    .removeValue()
                            }
                        }
                        Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show()
                        isFavorite.value = !isFavorite.value
                    }) {
                        Icon(Icons.Outlined.Favorite,
                            contentDescription = null,
                            modifier = Modifier
                                .height(28.dp)
                                .width(28.dp),
                            tint = Color.Red)
                    }
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.93f)
                .padding(top = 20.dp)) {
            if (!isInCart.value) {
                Counter(
                    increment = {
                        val item = data?.id?.let {
                            CartTable(it,
                                data.category,
                                1,
                                data.image,
                                data.price,
                                0.0,
                                data.title,
                                data.description,
                                data.rating)
                        }
                        if (item != null) {
                            dataBase?.cartDao()?.insert(item)
                            Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT).show()
                            isInCart.value = !isInCart.value
                        }
                    }, decrement = { }, Count = "0")
            } else {
                val item = data?.id?.let { dataBase?.cartDao()?.getSingleItem(it) }
                val data = item?.let {
                    CartTable(
                        it.id,
                        item.category,
                        item.count,
                        item.image,
                        item.price,
                        item.finalPrice,
                        item.title,
                        item.description,
                        item.rating
                    )
                }
                if (data != null) {
                    Counter(increment = {
                        if (dataBase != null) {
                            dataBase.cartDao()?.incrementCount(data.id)
                            dataBase.cartDao()?.setPrice(data.id)
                            GlobalScope.launch {
                                cartData.value = NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                            }
                        }
                    }, decrement = {
                        if (dataBase != null) {
                            dataBase.cartDao()?.decrementCount(data.id)
                            dataBase.cartDao()?.setPrice(data.id)
                            GlobalScope.launch {
                                if (data.count == 1) {
                                    isInCart.value = !isInCart.value
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
                    }, Count = data.count.toString())
                }
            }
            if (data != null) {
                Text(text = data.price.toString(),
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    modifier = Modifier.padding(start = 15.dp))
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
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(0.99f)) {
            Text(text = "Product Details",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.font_bold)),
                modifier = Modifier.padding(start = 15.dp, top = 10.dp))
            IconButton(onClick = {
                showMore.value = !showMore.value
            }) {
                if (showMore.value) {
                    Icon(Icons.Outlined.KeyboardArrowUp,
                        contentDescription = null,
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp))
                } else {
                    Icon(Icons.Outlined.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp))
                }
            }
        }
        if (data != null && showMore.value) {
            Text(
                text = data.description.toString(),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.font_medium)),
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp)
                    .fillMaxWidth(0.96f),
                color = Color.Gray.copy(alpha = 0.8f),
            )
        }
        Divider(
            thickness = 0.5.dp,
            color = Color.Gray.copy(alpha = 0.5f),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 15.dp)
                .align(alignment = Alignment.CenterHorizontally),
        )
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .padding(top = 10.dp)) {
            Text(text = "Review",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.font_bold)),
                modifier = Modifier.padding(start = 15.dp, top = 10.dp))
            Row {
                repeat(5) { index ->
                    if (data != null) {
                        Icon(Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier
                                .height(15.dp)
                                .width(15.dp),
                            tint = if (index < ratingCount) RatingColor else Color.Gray)
                    }

                }
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
    Column(modifier = Modifier.fillMaxHeight(0.98f), verticalArrangement = Arrangement.Bottom) {
        if (!isInCart.value) {
            CustomButton(title = "Add to Basket", onClick = {
                val item = data?.id?.let {
                    CartTable(it,
                        data.category,
                        1,
                        data.image,
                        data.price,
                        0.0,
                        data.title,
                        data.description,
                        data.rating)
                }
                if (item != null) {
                    dataBase?.cartDao()?.insert(item)
                    Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT).show()
                    isInCart.value = !isInCart.value
                }
            })
        } else {
            CustomButton(title = "Already in Basket ", onClick = {}, isShow = false)
        }
    }
}


@Composable
fun Counter(
    increment: () -> Unit,
    decrement: () -> Unit,
    Count: String,
) {
    Row(modifier = Modifier
        .fillMaxWidth(0.82f)
        .padding(start = 10.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = decrement) {
                Image(painterResource(id = R.drawable.minus),
                    null,
                    modifier = Modifier
                        .height(16.dp)
                        .width(12.dp))
            }
            Text(text = Count,
                fontFamily = FontFamily(Font(R.font.font_bold)),
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .border(
                        BorderStroke(color = Color.Gray, width = 0.5.dp),
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp))
            IconButton(onClick = increment) {
                Icon(Icons.Outlined.Add, null, tint = PrimaryGreen)
            }
        }
    }
}