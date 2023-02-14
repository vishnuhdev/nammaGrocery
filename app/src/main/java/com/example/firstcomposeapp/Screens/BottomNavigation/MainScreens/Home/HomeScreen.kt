package com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.home

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.firstcomposeapp.DatabaseHelper
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.apiService.ProductData
import com.example.firstcomposeapp.apiService.ProductDataInstance
import com.example.firstcomposeapp.apiService.roomDataBase.FavoriteTable
import com.example.firstcomposeapp.components.ListItemCard
import com.example.firstcomposeapp.components.ShimmerAnimation
import com.example.firstcomposeapp.ui.theme.PrimaryGreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(navController: NavHostController) {
    val products = ProductDataInstance.getProduct.getProductInfo()
    val data = remember { mutableStateOf(ProductData()) }


    var isLoading = rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = Unit) {
        isLoading.value = true
        val userId = Firebase.auth
        val namesRef = Firebase.database.reference
        val roomDataBase = DatabaseHelper.getInstance()
        userId.uid?.let { it ->
            namesRef.child("FavoriteList").child(it).get().addOnSuccessListener { snapshot ->
                val value = snapshot.children

                    val itemList = mutableListOf<FavoriteTable>()
                    value.forEach { entry ->
                        val item = entry.getValue(FavoriteTable::class.java)
                        Log.d("Entry",item.toString())
                        if (item != null) {
                            itemList.add(
                                item
                            )
                        }
                    }
                    Log.i("firebase", "got value $itemList")
                    if (roomDataBase != null) {
                        roomDataBase.favoriteDao()?.fireBaseInsert(itemList)
                        isLoading.value = false

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
                    .width(420.dp)
                    .height(120.dp)
            )
            Heading(title = "Exclusive Offer")
            LazyRow(
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize(1F)
            ) {
                items(data.value) { data ->
                    ListItemCard(data = data, navController = navController)
                }
            }
            Heading(title = "Snacks & Bakery Items")
            LazyRow(
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize(1F)
            ) {
                items(snacks) { data ->
                    ListItemCard(data = data, navController = navController)
                }
            }
            Heading(title = "Vegetables")
            LazyRow(
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize(1F)
            ) {
                items(vegetableList) { data ->
                    ListItemCard(data = data, navController = navController)
                }
            }
            Heading(title = "Meat & Fish")
            LazyRow(
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize(1F)
            ) {
                items(meat) { data ->
                    ListItemCard(data = data, navController = navController)
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