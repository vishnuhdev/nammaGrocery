package com.example.firstcomposeapp.Screens

import OtpField
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.theme.PrimaryGreen

@Composable
fun OtpScreen(){
    Column {
        IconButton(
            onClick = { /*...*/ },
            modifier = Modifier
                .height(48.dp)
                .width(32.dp)
                .padding(start = 5.dp, top = 10.dp)
                .fillMaxWidth()
        ){
            Icon(
                painter = painterResource(id = R.drawable.back_ios_new_24),
                contentDescription = null,
            )
        }
        Text(
            text = "Enter your 4-digit code",
            modifier = Modifier
                .padding(start = 10.dp, top = 80.dp ),
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.font_black)),
            textAlign = TextAlign.Center,
            fontSize = 21.sp
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp, top = 25.dp ),
        ) {
            Text(text = "Code",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.font_light)),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
                )
            Spacer(modifier = Modifier
                .height(20.dp))
            val (pinValue,onPinValueChange) = remember{
                mutableStateOf("")
            }
            OtpField(pinText = pinValue , onPinTextChange =onPinValueChange )
            Spacer(modifier = Modifier
                .height(65.dp))
            Box(modifier = Modifier
            ){
                TextButton(onClick = { /*TODO*/ }, shape =MaterialTheme.shapes.large) {

                    Text(text = "Resend Code",
                        color = PrimaryGreen,
                        fontFamily = FontFamily(Font(R.font.font_black)),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding( top = 16.dp),
                    )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth(0.95f),
                    horizontalArrangement = Arrangement.End
                ){
                    Button(onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors( PrimaryGreen),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_ios_new_24),
                            contentDescription = null,
                            modifier = Modifier
                                .rotate(180f)
                                .padding(8.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
