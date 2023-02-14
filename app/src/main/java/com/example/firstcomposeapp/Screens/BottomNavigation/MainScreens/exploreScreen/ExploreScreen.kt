package com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.exploreScreen

import android.annotation.SuppressLint
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.components.AppHeader
import com.example.firstcomposeapp.navigation.Graph

@Composable
fun ExploreScreen(navController: NavHostController) {

    val itemsList = listOf(
        Items("Fresh Fruits & Vegetable", R.drawable.veg, Color(0xFF53B175)),
        Items("Cooking Oil & Ghee", R.drawable.oil, Color(0xFFF8A44C)),
        Items("Meat & Fish", R.drawable.meat, Color(0xFFF7A593)),
        Items("Bakery & Snacks", R.drawable.bakery, Color(0xFFD3B0E0)),
        Items("Dairy & Eggs", R.drawable.dairy, Color(0xFFFDE598)),
        Items("Beverages", R.drawable.beverage, Color(0xFFB7DFF5))
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .background(color = Color.White)
//            .verticalScroll(state = scrollState)
    ) {
        AppHeader(
            Header = "Find Product",
            LeftImg = null,
            RightImg = null,
            RightonClick = {},
            LeftonClick = { /*TODO*/ })
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            content = {
                items(itemsList) { data ->
                    Card(name = data.name,
                        image = data.image,
                        color = data.color,
                        navController = navController)
                }
            })
    }
}


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Card(
    name: String,
    image: Int,
    color: Color?,
    navController: NavController,
) {
    val painter = painterResource(image)
    Card(
        onClick = {
            navController.navigate(Graph.HOME){
                popUpTo(Graph.HOME) {
                    inclusive = false
                }
            }
        },
        colors = CardDefaults.cardColors(Color.Transparent),
        border = color?.let { BorderStroke(1.dp, it) }
    ) {
        color?.let {
            Modifier
                .background(it.copy(alpha = 0.2f))
                .padding(15.dp, vertical = 35.dp)
                .fillMaxHeight()
                .fillMaxWidth()
        }?.let {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = it
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .height(95.dp)
                        .width(95.dp)
                )
                Text(
                    text = name,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

data class Items(
    val name: String,
    @DrawableRes val image: Int,
    @ColorRes val color: Color,
)