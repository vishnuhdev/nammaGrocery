package com.example.firstcomposeapp.Components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NormalTextInput(
    title: String,
    value : String,
    onValueChange :(String) -> Unit = {},
    Error : String
){
    Text(
        text = title,
        fontFamily = FontFamily(Font(R.font.font_light)),
        fontSize = 14.sp,
        modifier = Modifier
            .padding(start = 15.dp, top = 26.dp)
    )
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(value = value, onValueChange = { onValueChange(it)},
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(top = 7.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = PrimaryGreen,
                focusedLabelColor = PrimaryGreen,
                focusedSupportingTextColor = PrimaryGreen,
                cursorColor= PrimaryGreen
            ),
        )
        Text(
            text = Error,
            color = Color.Red,
            fontFamily = FontFamily(Font(R.font.font_medium)),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 5.dp)

        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassWordInput(
    title: String,
    value : String,
    onValueChange :(String) -> Unit = {},
    Error : String
){
    val showPassword = rememberSaveable{
        mutableStateOf(false)
    }

    Text(
        text = title,
        fontFamily = FontFamily(Font(R.font.font_light)),
        fontSize = 14.sp,
        modifier = Modifier
            .padding(start = 15.dp, top = 10.dp)
    )
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = value,
            onValueChange = { onValueChange(it)},
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(top = 7.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = PrimaryGreen,
                focusedLabelColor = PrimaryGreen,
                focusedSupportingTextColor = PrimaryGreen,
                cursorColor = PrimaryGreen
            ),
            singleLine = true,
            visualTransformation =
            if (!showPassword.value)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = {
                    showPassword.value = !showPassword.value
                }) {
                    Icon(
                        painter =
                        if (!showPassword.value) painterResource(id = R.drawable.ic_outline_remove_red_eye_24)
                        else
                            painterResource(id = R.drawable.hide),
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp),
                        contentDescription = null
                    )
                }
            },
        )
        Text(
            text = Error,
            color = Color.Red,
            fontFamily = FontFamily(Font(R.font.font_medium)),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 3.dp)

        )
    }
}
