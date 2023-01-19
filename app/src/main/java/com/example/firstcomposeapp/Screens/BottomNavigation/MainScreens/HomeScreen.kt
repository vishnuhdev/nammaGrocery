package com.example.firstcomposeapp.Screens.BottomNavigation.MainScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.firstcomposeapp.Components.ListItemCard
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
//    TextButton(
//        onClick = { navController.navigate(Graph.DETAILS)  }
//    )
//    {
//        Text(text = "Click")
//
//    }
    var ScrollState = rememberScrollState()
    val (value, onValueChange) = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .verticalScroll(state = ScrollState)
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
        TextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Black) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.98f)
                .background(Color(0xFFF2F3F2), RoundedCornerShape(14.dp)),
            placeholder = {
                Text(
                    text = "Search Store",
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    color = Color.Gray
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent,
                cursorColor = Color.DarkGray
            )
        )
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
            items(10) {
                ListItemCard()
            }
        }
        Heading(title = "Best Selling")
        LazyRow(
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxSize(1F)
        ) {
            items(10) {
                ListItemCard()
            }
        }
        Heading(title = "Groceries")
        LazyRow(
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(10) {
                ListItemCard()
            }
        }
    }
}

@Composable
fun Heading(
    title : String
){
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