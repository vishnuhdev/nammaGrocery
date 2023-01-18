package com.example.firstcomposeapp.Components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.theme.Blue
import com.example.firstcomposeapp.ui.theme.PrimaryGreen

@Composable
fun ButtonWithImage(
    title:String,
    image : Int,
    onClick: () -> Unit
)
{
    val painter = painterResource(image)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(onClick = onClick,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.31f),
            colors = ButtonDefaults.buttonColors(Blue),
        )
        {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier
                .padding(start = 15.dp))

            Text(
                text = title ,
                fontSize = 15.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.font_black)),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 15.dp , bottom = 15.dp)
            )
    }

    }
}


@Composable
fun CustomButton(
    title:String,
    onClick: () -> Unit,
    isShow : Boolean = true
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(onClick = onClick,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(0.95f),
            colors = ButtonDefaults.buttonColors(PrimaryGreen),
            enabled = isShow,
        )
        {
            Text(
                text = title ,
                fontSize = 17.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.font_bold)),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 10.dp , bottom = 10.dp)
            )
        }

    }
}