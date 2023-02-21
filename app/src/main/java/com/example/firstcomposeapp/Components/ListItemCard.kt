@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.firstcomposeapp.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.firstcomposeapp.navigation.DetailsScreen
import com.example.firstcomposeapp.navigation.Graph
import com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.home.Counter
import com.example.firstcomposeapp.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemCard(
    data: ProductDataItem,
    navController: NavController,
    isInCart: Boolean,
    increment: () -> Unit,
    decrement: () -> Unit,
    adderOnClick : () -> Unit,
    count : String,
) {
    val cartData = remember { mutableStateOf(emptyList<CartTable>()) }

    LaunchedEffect(key1 = cartData, isInCart) {
        cartData.value = NammaGroceryDB.getInstance()?.cartDao()?.getAll()!!
    }

    Card(
        onClick = {
           navController.currentBackStackEntry?.savedStateHandle?.apply {
               set(DetailsScreen.DetailArgs.ProductData , data)
           }
            navController.navigate(Graph.DETAILS) {
                popUpTo(Graph.DETAILS) {
                    inclusive = true
                }
            }
        },
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Box(
            modifier = Modifier
                .border(
                    border = BorderStroke(0.5.dp, Color.Gray),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(color = Color.White)
                .padding(10.dp)
                .width(150.dp)
        ) {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(data.image),
                    contentDescription = "Image",
                    modifier = Modifier
                        .height(79.dp)
                        .width(99.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
                Text(
                    text = data.title.toString() ,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontSize = 18.sp,
                )
                Spacer(
                    modifier = Modifier
                        .height(5.dp)
                )
                Text(
                    text = "7pcs,Price g",
                    fontFamily = FontFamily(Font(R.font.font_medium)),
                    color = Color.Gray,
                    fontSize = 14.sp,
                )
                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.price.toString(),
                        fontFamily = FontFamily(Font(R.font.font_bold)),
                        fontSize = 16.sp,
                    )
                    if(isInCart){
                        Counter(increment = increment, decrement = decrement, Count = count )
                    }else{
                        CustomAdder(onClick = adderOnClick)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomAdder(
    onClick: () -> Unit,
) {
    Column {
        Button(
            onClick = onClick,
            modifier = Modifier
                .height(45.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(PrimaryGreen),
        ) {
            Icon(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
        }
    }
}
