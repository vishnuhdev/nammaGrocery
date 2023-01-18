package com.example.firstcomposeapp.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Card
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.theme.PrimaryGreen

@Composable
fun ListItemCard() {
    Card(
        modifier = Modifier
            .border(
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = Color.White)
            .padding(15.dp)
            .width(135.dp),
        onClick = {},

    ) {
        Column{
            Image(
                painter = painterResource(id = R.drawable.banana),
                contentDescription = "Image",
                modifier = Modifier
                    .height(79.dp)
                    .width(99.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier
                .height(20.dp))
            Text(
                text = "Organic Bananas",
                fontFamily = FontFamily(Font(R.font.font_bold)),
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier
                .height(5.dp))
            Text(
                text = "7pcs,Price g",
                fontFamily = FontFamily(Font(R.font.font_medium)),
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier
                .height(15.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₹400",
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontSize = 16.sp,
                )
                CustomAdder(onClick = {})
            }
        }
    }
}

@Composable
fun CustomAdder(
    onClick: () -> Unit,
) {
    Column{
        Button(onClick = onClick,
            modifier = Modifier
                .height(45.dp)
                .clip(RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(PrimaryGreen),
        ){
            Image(
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp),
                painter = painterResource(id = R.drawable.plus) ,
                contentDescription =null,
                )
        }

    }
}
