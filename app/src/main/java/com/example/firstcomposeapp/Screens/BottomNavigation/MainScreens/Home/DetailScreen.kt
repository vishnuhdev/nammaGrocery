package com.example.firstcomposeapp.Screens.BottomNavigation.MainScreens.Home

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.firstcomposeapp.ApiService.ProductDataItem
import com.example.firstcomposeapp.Components.AppHeader
import com.example.firstcomposeapp.Components.CustomButton
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.theme.PrimaryGreen
import com.example.firstcomposeapp.ui.theme.RatingColor
import com.example.nestednavigationbottombardemo.graphs.DetailsScreen

@Composable
fun DetailsScreen(navController: NavController) {

    val scrollState = rememberScrollState()
    var showMore = rememberSaveable {
        mutableStateOf(false)
    }

    val data =
        navController.previousBackStackEntry?.savedStateHandle?.get<ProductDataItem>(DetailsScreen.DetailArgs.ProductData)
    Log.d("Detail", data.toString())
    val ratingCount = data?.rating?.toFloatOrNull()?.toInt() ?: 0

    Column(modifier = Modifier
        .fillMaxHeight(0.89f)
        .verticalScroll(state = scrollState)
        .background(color = Color.White)
    ) {
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
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp))
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 20.dp)) {
            Counter()
            if (data != null) {
                Text(text = data.price.toString(),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    modifier = Modifier.padding(start = 20.dp))
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.99f)
        ) {
            Text(text = "Product Details",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.font_bold)),
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp))
            IconButton(onClick = {
                showMore.value = !showMore.value
            }) {
                if (showMore.value) {
                    Icon(
                        Icons.Outlined.KeyboardArrowUp,
                        contentDescription = null,
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp))
                } else {
                    Icon(
                        Icons.Outlined.KeyboardArrowDown,
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .padding( top = 10.dp)
        ) {
            Text(text = "Review",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.font_bold)),
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp))
            Row {
                repeat(5) { index ->
                    if (data != null) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier
                                .height(15.dp)
                                .width(15.dp),
                            tint = if (index < ratingCount) RatingColor else Color.Gray
                        )
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
    Column(
        modifier = Modifier
            .fillMaxHeight(0.98f),
        verticalArrangement = Arrangement.Bottom
    ) {
        CustomButton(title = "Add to Basket", onClick = { /*TODO*/ })
    }
}


@Composable
fun Counter() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { /*TODO*/ }) {
            Image(painterResource(id = R.drawable.minus),
                null,
                modifier = Modifier
                    .height(16.dp)
                    .width(12.dp))
        }
        Text(text = "0",
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
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.Add, null, tint = PrimaryGreen)
        }
    }
}
