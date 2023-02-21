@file:Suppress("NAME_SHADOWING", "OPT_IN_IS_NOT_ENABLED")

package com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.firstcomposeapp.NammaGroceryDB
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.apiService.ProductData
import com.example.firstcomposeapp.apiService.ProductDataInstance
import com.example.firstcomposeapp.apiService.roomDataBase.CartTable
import com.example.firstcomposeapp.apiService.roomDataBase.FavoriteTable
import com.example.firstcomposeapp.components.ListItemCard
import com.example.firstcomposeapp.components.ShimmerAnimation
import com.example.firstcomposeapp.ui.theme.PrimaryGreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(navController: NavHostController) {
    val products = ProductDataInstance.getProduct.getProductInfo()
    val data = remember { mutableStateOf(ProductData()) }
    val cartData = remember { mutableStateOf(emptyList<CartTable>()) }

    val isLoading = rememberSaveable {
        mutableStateOf(true)
    }

    val dataBase = NammaGroceryDB.getInstance()
    val firebaseDB = Firebase.database.reference
    val userId = Firebase.auth
    val context = LocalContext.current

//    val carouselImages = listOf(
//        "https://www.bigbasket.com/media/uploads/banner_images/hp_bcd_m_bcd_250123_400.jpg",
//        "https://www.bigbasket.com/media/uploads/banner_images/hp_m_health_suppliment_250123_400.jpg",
//        "https://www.bigbasket.com/media/uploads/banner_images/hp_m_babycare_250123_400.jpg",
//        "https://www.bigbasket.com/media/uploads/banner_images/hp_m_petstore_250123_400.jpg",
//        "https://www.bigbasket.com/media/uploads/banner_images/hp_m_cmc_mahaShivratri_400_140223.jpg",
//        "https://www.bigbasket.com/media/uploads/banner_images/HP_EMF_M_WeekdayBangalore-1600x460-230213.jpeg",
//        "https://www.bigbasket.com/media/uploads/banner_images/hp_m_Dry_FishBanner_1600x460_070922.jpg",
//    )

    LaunchedEffect(key1 = cartData) {
        cartData.value = NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
    }

    LaunchedEffect(key1 = Unit) {
        isLoading.value = true
        val userId = Firebase.auth
        val namesRef = Firebase.database.reference
        val favoriteDataBase = NammaGroceryDB.getInstance()
        cartData.value = NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!

        userId.uid?.let { it ->
            namesRef.child("FavoriteList").child(it).get().addOnSuccessListener { snapshot ->
                val value = snapshot.children
                val itemList = mutableListOf<FavoriteTable>()
                value.forEach { entry ->
                    val item = entry.getValue(FavoriteTable::class.java)
                    if (item != null) {
                        itemList.add(item)
                    }
                }
                Log.i("firebase", "got value $itemList")
                if (favoriteDataBase != null) {
                    favoriteDataBase.favoriteDao()?.fireBaseInsert(itemList)
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }
        userId.uid?.let { it ->
            namesRef.child("CartList").child(it).get().addOnSuccessListener { snapshot ->
                val value = snapshot.children
                val itemList = mutableListOf<CartTable>()
                value.forEach { entry ->
                    val item = entry.getValue(CartTable::class.java)
                    if (item != null) {
                        itemList.add(item)
                    }
                }
                Log.i("firebase", "got value $itemList")
                if (favoriteDataBase != null) {
                    favoriteDataBase.cartDao()?.fireBaseInsert(itemList)
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }
        products.enqueue(object : Callback<ProductData> {
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                isLoading.value = true
                val productData = response.body()
                isLoading.value = false
                if (productData != null) {
                    data.value = productData
                }
            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {
                Log.d("Err", t.toString())
            }
        })
    }

    val scrollState = rememberScrollState()
    val vegetableList = data.value.filter { it.category == "Vegetables" }
    val snacks = data.value.filter { it.category == "Snacks" || it.category == "Bakery" }
    val meat = data.value.filter { it.category == "Meat" || it.category == "Fish" }

    if (isLoading.value) {
        ShimmerEff()
    } else {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.93f)
                .verticalScroll(state = scrollState)
                .background(color = Color.White)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .height(38.dp)
                    .width(38.dp)
                    .padding(top = 10.dp)
            )
            Row(
                modifier = Modifier
                    .padding(top = 10.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_location_on_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
                Text(
                    text = "Chennai,TN",
                    fontFamily = FontFamily(Font(R.font.font_medium)),
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 2.dp)
                )
            }
//            TextField(
//                value = value,
//                onValueChange = onValueChange,
//                leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Black) },
//                modifier = Modifier
//                    .padding(10.dp)
//                    .fillMaxWidth(0.98f)
//                    .background(Color(0xFFF2F3F2), RoundedCornerShape(14.dp)),
//                placeholder = {
//                    Text(
//                        text = "Search Store",
//                        fontFamily = FontFamily(Font(R.font.font_bold)),
//                        color = Color.Gray
//                    )
//                },
//                colors = TextFieldDefaults.textFieldColors(
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    containerColor = Color.Transparent,
//                    cursorColor = Color.DarkGray
//                )
//            )
            Spacer(modifier = Modifier
                .height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.banner),
                null,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(120.dp)
            )
//            CarouselSlider(imageUrls = carouselImages)
            Heading(title = "Exclusive Offer")
            LazyRow(
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize(1F)
            ) {
                items(data.value) { data ->
                    val cartMatch = cartData.value.any { it.id == data.id }
                    val isInCart = remember { mutableStateOf(cartMatch) }
                    isInCart.value = cartMatch
                    val item = data.id?.let { dataBase?.cartDao()?.getSingleItem(it) }
                    val cartItem = item?.let {
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

                    ListItemCard(data = data,
                        isInCart = isInCart.value,
                        navController = navController,
                        increment = {
                            if (dataBase != null) {
                                data.id?.let { dataBase.cartDao()?.incrementCount(it) }
                                data.id?.let { dataBase.cartDao()?.setPrice(it) }
                                GlobalScope.launch {
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                    val namesRef = firebaseDB.child("CartList")
                                    val key = data.id
                                    val data =
                                        data.id?.let {
                                            NammaGroceryDB.getInstance()?.cartDao()
                                                ?.getSingleItem(it)
                                        }
                                    userId.uid?.let {
                                        if (key != null) {
                                            namesRef.child(it).child(key).setValue(data)
                                        }
                                    }
                                }
                            }
                        }, decrement = {
                            if (dataBase != null) {
                                data.id?.let { dataBase.cartDao()?.decrementCount(it) }
                                data.id?.let { dataBase.cartDao()?.setPrice(it) }
                                val namesRef = firebaseDB.child("CartList")
                                val key = data.id
                                GlobalScope.launch {
                                    if (cartItem != null) {
                                        if (cartItem.count == 1) {
                                            isInCart.value = !isInCart.value
                                            val item = CartTable(
                                                cartItem.id,
                                                cartItem.category,
                                                cartItem.count,
                                                cartItem.image,
                                                cartItem.price,
                                                0.0,
                                                cartItem.title,
                                                cartItem.description,
                                                cartItem.rating
                                            )
                                            dataBase.cartDao()?.delete(item)
                                            if (key != null) {
                                                namesRef.child(key).removeValue()
                                            }
                                        }
                                    }
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                    val data =
                                        data.id?.let {
                                            NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(
                                                it)
                                        }
                                    userId.uid?.let {
                                        if (key != null) {
                                            namesRef.child(it).child(key).setValue(data)
                                        }
                                    }
                                }
                            }
                        }, count = cartItem?.count.toString(),
                        adderOnClick = {
                            val item = data.id?.let {
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
                                val namesRef = firebaseDB.child("CartList")
                                val key = data.id
                                val data =
                                    NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(data.id)
                                userId.uid?.let {
                                    namesRef.child(it).child(key).setValue(data)
                                }
                                Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT)
                                    .show()
                                isInCart.value = !isInCart.value
                                GlobalScope.launch {
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                }
                            }
                        }
                    )
                }
            }
            Heading(title = "Snacks & Bakery Items")
            LazyRow(
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize(1F)
            ) {
                items(snacks) { data ->
                    val cartMatch = cartData.value.any { it.id == data.id }
                    val isInCart = remember { mutableStateOf(cartMatch) }
                    isInCart.value = cartMatch
                    val item = data.id?.let { dataBase?.cartDao()?.getSingleItem(it) }
                    val cartItem = item?.let {
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

                    ListItemCard(data = data,
                        isInCart = isInCart.value,
                        navController = navController,
                        increment = {
                            if (dataBase != null) {
                                data.id?.let { dataBase.cartDao()?.incrementCount(it) }
                                data.id?.let { dataBase.cartDao()?.setPrice(it) }
                                GlobalScope.launch {
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                    val namesRef = firebaseDB.child("CartList")
                                    val key = data.id
                                    val data =
                                        data.id?.let {
                                            NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(
                                                it)
                                        }
                                    userId.uid?.let {
                                        if (key != null) {
                                            namesRef.child(it).child(key).setValue(data)
                                        }
                                    }
                                }
                            }
                        }, decrement = {
                            if (dataBase != null) {
                                data.id?.let { dataBase.cartDao()?.decrementCount(it) }
                                data.id?.let { dataBase.cartDao()?.setPrice(it) }
                                val namesRef = firebaseDB.child("CartList")
                                val key = data.id
                                GlobalScope.launch {
                                    if (cartItem != null) {
                                        if (cartItem.count == 1) {
                                            isInCart.value = !isInCart.value
                                            val item = CartTable(
                                                cartItem.id,
                                                cartItem.category,
                                                cartItem.count,
                                                cartItem.image,
                                                cartItem.price,
                                                0.0,
                                                cartItem.title,
                                                cartItem.description,
                                                cartItem.rating
                                            )
                                            dataBase.cartDao()?.delete(item)
                                            if (key != null) {
                                                namesRef.child(key).removeValue()
                                            }
                                        }
                                    }
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                    val data =
                                        data.id?.let {
                                            NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(
                                                it)
                                        }
                                    userId.uid?.let {
                                        if (key != null) {
                                            namesRef.child(it).child(key).setValue(data)
                                        }
                                    }
                                }
                            }
                        }, count = cartItem?.count.toString(),
                        adderOnClick = {
                            val item = data.id?.let {
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
                                val namesRef = firebaseDB.child("CartList")
                                val key = data.id
                                val data =
                                    NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(data.id)
                                userId.uid?.let {
                                    namesRef.child(it).child(key).setValue(data)
                                }
                                Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT)
                                    .show()
                                isInCart.value = !isInCart.value
                                GlobalScope.launch {
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                }
                            }
                        }
                    )
                }
            }
            Heading(title = "Vegetables")
            LazyRow(
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize(1F)
            ) {
                items(vegetableList) { data ->
                    val cartMatch = cartData.value.any { it.id == data.id }
                    val isInCart = remember { mutableStateOf(cartMatch) }
                    isInCart.value = cartMatch
                    val item = data.id?.let { dataBase?.cartDao()?.getSingleItem(it) }
                    val cartItem = item?.let {
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

                    ListItemCard(data = data,
                        isInCart = isInCart.value,
                        navController = navController,
                        increment = {
                            if (dataBase != null) {
                                data.id?.let { dataBase.cartDao()?.incrementCount(it) }
                                data.id?.let { dataBase.cartDao()?.setPrice(it) }
                                GlobalScope.launch {
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                    val namesRef = firebaseDB.child("CartList")
                                    val key = data.id
                                    val data =
                                        data.id?.let {
                                            NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(
                                                it)
                                        }
                                    userId.uid?.let {
                                        if (key != null) {
                                            namesRef.child(it).child(key).setValue(data)
                                        }
                                    }
                                }
                            }
                        }, decrement = {
                            if (dataBase != null) {
                                data.id?.let { dataBase.cartDao()?.decrementCount(it) }
                                data.id?.let { dataBase.cartDao()?.setPrice(it) }
                                val namesRef = firebaseDB.child("CartList")
                                val key = data.id
                                GlobalScope.launch {
                                    if (cartItem != null) {
                                        if (cartItem.count == 1) {
                                            isInCart.value = !isInCart.value
                                            val item = CartTable(
                                                cartItem.id,
                                                cartItem.category,
                                                cartItem.count,
                                                cartItem.image,
                                                cartItem.price,
                                                0.0,
                                                cartItem.title,
                                                cartItem.description,
                                                cartItem.rating
                                            )
                                            dataBase.cartDao()?.delete(item)
                                            if (key != null) {
                                                namesRef.child(key).removeValue()
                                            }
                                        }
                                    }
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                    val data =
                                        data.id?.let {
                                            NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(
                                                it)
                                        }
                                    userId.uid?.let {
                                        if (key != null) {
                                            namesRef.child(it).child(key).setValue(data)
                                        }
                                    }
                                }
                            }
                        }, count = cartItem?.count.toString(),
                        adderOnClick = {
                            val item = data.id?.let {
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
                                val namesRef = firebaseDB.child("CartList")
                                val key = data.id
                                val data =
                                    NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(data.id)
                                userId.uid?.let {
                                    namesRef.child(it).child(key).setValue(data)
                                }
                                Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT)
                                    .show()
                                isInCart.value = !isInCart.value
                                GlobalScope.launch {
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                }
                            }
                        }
                    )
                }
            }
            Heading(title = "Meat & Fish")
            LazyRow(
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize(1F)
            ) {
                items(meat) { data ->
                    val cartMatch = cartData.value.any { it.id == data.id }
                    val isInCart = remember { mutableStateOf(cartMatch) }
                    isInCart.value = cartMatch
                    val item = data.id?.let { dataBase?.cartDao()?.getSingleItem(it) }
                    val cartItem = item?.let {
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

                    ListItemCard(data = data,
                        isInCart = isInCart.value,
                        navController = navController,
                        increment = {
                            if (dataBase != null) {
                                data.id?.let { dataBase.cartDao()?.incrementCount(it) }
                                data.id?.let { dataBase.cartDao()?.setPrice(it) }
                                GlobalScope.launch {
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                    val namesRef = firebaseDB.child("CartList")
                                    val key = data.id
                                    val data =
                                        data.id?.let {
                                            NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(
                                                it)
                                        }
                                    userId.uid?.let {
                                        if (key != null) {
                                            namesRef.child(it).child(key).setValue(data)
                                        }
                                    }
                                }
                            }
                        },
                        adderOnClick = {
                            val item = data.id?.let {
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
                                val namesRef = firebaseDB.child("CartList")
                                val key = data.id
                                val data =
                                    NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(data.id)
                                userId.uid?.let {
                                    namesRef.child(it).child(key).setValue(data)
                                }
                                Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT)
                                    .show()
                                isInCart.value = !isInCart.value
                                GlobalScope.launch {
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                }
                            }
                        },
                        decrement = {
                            if (dataBase != null) {
                                data.id?.let { dataBase.cartDao()?.decrementCount(it) }
                                data.id?.let { dataBase.cartDao()?.setPrice(it) }
                                val namesRef = firebaseDB.child("CartList")
                                val key = data.id
                                GlobalScope.launch {
                                    if (cartItem != null) {
                                        if (cartItem.count == 1) {
                                            isInCart.value = !isInCart.value
                                            val item = CartTable(
                                                cartItem.id,
                                                cartItem.category,
                                                cartItem.count,
                                                cartItem.image,
                                                cartItem.price,
                                                0.0,
                                                cartItem.title,
                                                cartItem.description,
                                                cartItem.rating
                                            )
                                            dataBase.cartDao()?.delete(item)
                                            if (key != null) {
                                                namesRef.child(key).removeValue()
                                            }
                                        }
                                    }
                                    cartData.value =
                                        NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
                                    val data =
                                        data.id?.let {
                                            NammaGroceryDB.getInstance()?.cartDao()?.getSingleItem(
                                                it)
                                        }
                                    userId.uid?.let {
                                        if (key != null) {
                                            namesRef.child(it).child(key).setValue(data)
                                        }
                                    }
                                }
                            }
                        }, count = cartItem?.count.toString()
                    )
                }
            }
        }
    }
}

@Composable
fun Heading(
    title: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontFamily = FontFamily(Font(R.font.font_bold)),
            fontSize = 24.sp,
            color = Color.Black
        )
        TextButton(onClick = {}) {
            Text(
                text = "See all",
                fontFamily = FontFamily(Font(R.font.font_medium)),
                fontSize = 16.sp,
                color = PrimaryGreen
            )
        }
    }
}

@Composable
fun ShimmerEff() {
    LazyColumn {
        repeat(1) {
            item {
                ShimmerAnimation()
            }
        }
    }
}

//@SuppressLint("RememberReturnType")
//@Composable
//fun CarouselSlider(imageUrls: List<String>) {
//    var currentPage by remember { mutableStateOf(0) }
//    val pagingConfig = PagingConfig(pageSize = 1)
//    val pager = remember { Pager(pagingConfig) { imageUrls } }
//    val pageItems = pager.flow.collectAsLazyPagingItems()
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//    ) {
//        itemsIndexed(pageItems) { index, item ->
//            if (item != null) {
//                Image(
//                    painter =rememberAsyncImagePainter(item),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                )
//            }
//        }
//    }
//}

