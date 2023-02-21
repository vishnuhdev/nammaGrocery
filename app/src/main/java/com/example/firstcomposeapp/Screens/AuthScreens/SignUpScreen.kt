package com.example.firstcomposeapp.screens.authScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.Screen
import com.example.firstcomposeapp.components.CustomButton
import com.example.firstcomposeapp.ui.theme.PrimaryGreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {

    var database = Firebase.database.reference
    var auth = Firebase.auth
    val isLoading = rememberSaveable {
        mutableStateOf(false)
    }

    var scrollState = rememberScrollState()

    val userName = rememberSaveable {
        mutableStateOf("")
    }
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val passWord = rememberSaveable {
        mutableStateOf("")
    }
    val showPassword = rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
    ) {
        if (isLoading.value) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.Black,
                trackColor = Color.White
            )
        } else {
            null
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .height(85.dp)
                .width(85.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(text = stringResource(id = R.string.signup),
            fontFamily = FontFamily(Font(R.font.font_bold)),
            fontSize = 26.sp,
            modifier = Modifier
                .padding(start = 15.dp, top = 35.dp)
        )
        Text(
            text = "Enter your credentials to continue",
            fontFamily = FontFamily(Font(R.font.font_light)),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.username),
            fontFamily = FontFamily(Font(R.font.font_light)),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 15.dp, top = 26.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = userName.value,
                onValueChange = { text ->
                    userName.value = text
                },
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .padding(top = 7.dp),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = PrimaryGreen,
                    focusedLabelColor = PrimaryGreen,
                    focusedSupportingTextColor = PrimaryGreen,
                    cursorColor = PrimaryGreen
                ),
            )
        }
        Text(
            text = stringResource(id = R.string.email),
            fontFamily = FontFamily(Font(R.font.font_light)),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 15.dp, top = 12.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email.value,
                onValueChange = { text ->
                    email.value = text
                },
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .padding(top = 7.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = PrimaryGreen,
                    focusedLabelColor = PrimaryGreen,
                    focusedSupportingTextColor = PrimaryGreen,
                    cursorColor = PrimaryGreen
                ),
            )
        }
        Text(
            text = stringResource(id = R.string.password),
            fontFamily = FontFamily(Font(R.font.font_light)),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = passWord.value,
                onValueChange = { text ->
                    passWord.value = text
                },
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
                            if (!showPassword.value)
                                painterResource(id = R.drawable.ic_outline_remove_red_eye_24)
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
        }
        Spacer(modifier = Modifier
            .height(12.dp))
        Text(text = stringResource(id = R.string.terms),
            fontFamily = FontFamily(Font(R.font.font_medium)),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp),
            color = Color.Black
        )
        Spacer(modifier = Modifier
            .height(25.dp))
        CustomButton(
            title = stringResource(id = R.string.signup),
            price = null,
            isShow = !isLoading.value,
            onClick = {
                isLoading.value = true
                auth.createUserWithEmailAndPassword(
                    email.value.trim(),
                    passWord.value.trim()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userInfo = UserInfo(userName.value,email.value,passWord.value)
                            auth.uid?.let { database.child("AuthInfo").child(it).setValue(userInfo) }
                            Log.d("AUTH", "signInWithEmail:success")
                            isLoading.value = false
                            val user = auth.currentUser
                        } else {
                            Log.w("AUTH", "signInWithEmail:failure:${task.exception}")
                            isLoading.value = false
                        }
                    }
            }

        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {
                    navController.navigate(Screen.LoginWithEmail.route) {
                        popUpTo(Screen.LoginWithEmail.route) {
                            inclusive = true
                        }
                    }
                },
            ) {
                Text(
                    text = stringResource(id = R.string.alreadyhaveacc),
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = stringResource(id = R.string.login),
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontSize = 14.sp,
                    color = PrimaryGreen
                )
            }
        }
    }
}

data class UserInfo(
    val userName: String,
    val emailId: String,
    val password: String,
)