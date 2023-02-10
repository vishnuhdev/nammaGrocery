package com.example.firstcomposeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.theme.PrimaryGreen

@Composable
fun Spash(){
    Box(modifier = Modifier
        .background(
            color = PrimaryGreen
        )
        .fillMaxHeight()
        .fillMaxWidth()
    )
    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.icon_logo), contentDescription =null,
            modifier = Modifier
                .size(60.dp))
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Namma Grocery" ,
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.font_black))
        )
    }
}
