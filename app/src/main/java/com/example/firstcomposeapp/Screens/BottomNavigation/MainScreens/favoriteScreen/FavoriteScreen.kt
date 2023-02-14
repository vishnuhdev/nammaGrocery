package com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.favoriteScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.firstcomposeapp.DatabaseHelper
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.apiService.roomDataBase.FavoriteTable
import com.example.firstcomposeapp.components.AppHeader
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun FavoriteScreen(navController: NavHostController) {
    val favorData = remember { mutableStateOf(emptyList<FavoriteTable>()) }
    val context = LocalContext.current

    LaunchedEffect(key1 = favorData) {
        favorData.value = DatabaseHelper.getInstance()?.favoriteDao()?.getAll()!!
    }
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxHeight(0.93f)
            .background(color = Color.White)
    ) {
        AppHeader(
            Header = "Favorite",
            LeftImg = null,
            RightImg = null,
            LeftonClick = { /*TODO*/ },
            RightonClick = {}
        )
        if (favorData.value.isEmpty()) {
            val composition by rememberLottieComposition(LottieCompositionSpec.Asset("wishlist.json"))
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
                    text = "You haven't added any Items yet",
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Click ❤ to add favorites",
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
            items(favorData.value) { data ->
                FavourCard(data = data, navController = navController) {
                    val item = FavoriteTable(
                        data.id,
                        data.category,
                        data.image,
                        data.price,
                        data.title,
                        data.description,
                        data.rating
                    )
                    DatabaseHelper.getInstance()?.favoriteDao()?.delete(
                        favItems = item
                    )
                    val userId = Firebase.auth
                    val database = Firebase.database.reference
                    userId.uid?.let {
                        database.child("FavoriteList").child(it).child(data.id).removeValue()
                    }
                    GlobalScope.launch {
                        favorData.value = DatabaseHelper.getInstance()?.favoriteDao()?.getAll()!!
                    }
                    Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavourCard(data: FavoriteTable, navController: NavHostController, onClick: () -> Unit) {

    Card(
        onClick = {},
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier
//                .padding(start = 25.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(data.image),
                null,
                modifier = Modifier
                    .height(75.dp)
                    .width(75.dp)
            )
            Spacer(modifier = Modifier
//                .padding(start = 25.dp)
            )
            Column {
                Text(
                    text = data.title.toString(),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.font_bold))
                )
                Spacer(modifier = Modifier
                    .height(5.dp))
                Text(
                    text = "325ml,Price",
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.font_light))
                )
            }
            Spacer(modifier = Modifier
                .padding(start = 80.dp)
            )
            Text(
                text = data.price.toString(),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.font_bold))
            )
            IconButton(onClick = onClick) {
                Icon(Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .height(32.dp)
                        .width(32.dp),
                    tint = Color.Red
                )
            }

        }
    }
}