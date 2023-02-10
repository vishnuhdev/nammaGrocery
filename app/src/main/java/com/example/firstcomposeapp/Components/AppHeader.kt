package com.example.firstcomposeapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeapp.R

@Composable
fun AppHeader(
    Header: String?,
    LeftImg: Int?,
    RightImg: Int?,
    LeftonClick: () -> Unit,
    RightonClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val leftImg = LeftImg?.let { painterResource(it) }
        val rightImg = RightImg?.let { painterResource(it) }

        IconButton(
            onClick = LeftonClick,
            modifier = Modifier
                .height(48.dp)
                .width(48.dp)
                .padding(start = 5.dp, top = 10.dp)
                .fillMaxWidth()
        ) {
            if (leftImg != null) {
                Icon(
                    painter = leftImg,
                    contentDescription = null,
                )
            }
        }
        if (Header != null) {
            Text(
                text = Header,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.font_black)),
                modifier = Modifier
                    .padding(top = 10.dp)
            )
        }
        IconButton(
            onClick = RightonClick,
            modifier = Modifier
                .height(48.dp)
                .width(48.dp)
                .padding(start = 5.dp, top = 10.dp)
                .fillMaxWidth()
        ) {
            if (rightImg != null) {
                Icon(
                    painter = rightImg,
                    contentDescription = null,
                )
            }
        }
    }
}