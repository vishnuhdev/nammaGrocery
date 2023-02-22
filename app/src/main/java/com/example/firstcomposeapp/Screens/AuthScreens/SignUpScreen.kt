@file:Suppress("OPT_IN_IS_NOT_ENABLED", "UNUSED_EXPRESSION")

package com.example.firstcomposeapp.screens.authScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.Screen
import com.example.firstcomposeapp.components.CustomButton
import com.example.firstcomposeapp.components.CustomLoader
import com.example.firstcomposeapp.components.NormalTextInput
import com.example.firstcomposeapp.components.PassWordInput
import com.example.firstcomposeapp.ui.theme.PrimaryGreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@Composable
fun SignUpScreen(navController: NavController) {

    val database = Firebase.database.reference
    val auth = Firebase.auth
    val isLoading = rememberSaveable {
        mutableStateOf(false)
    }

    val scrollState = rememberScrollState()

    val userName = rememberSaveable {
        mutableStateOf("")
    }
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val passWord = rememberSaveable {
        mutableStateOf("")
    }
    val err = rememberSaveable {
        mutableStateOf("")
    }

    fun validation() {
        if (email.value.isEmpty() or passWord.value.isNotEmpty() or userName.value.isEmpty()) {
            err.value = "Please fill the detail"
        }
        if (email.value.isNotEmpty() and passWord.value.isNotEmpty() and userName.value.isNotEmpty())
        {
            isLoading.value = true
            auth.createUserWithEmailAndPassword(
                email.value.trim(),
                passWord.value.trim()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userInfo = UserInfo(userName.value, email.value, passWord.value)
                        auth.uid?.let {
                            database.child("AuthInfo").child(it).setValue(userInfo)
                        }
                        Log.d("AUTH", "signInWithEmail:success")
                        isLoading.value = false
                    } else {
                        Log.w("AUTH", "signInWithEmail:failure:${task.exception}")
                        isLoading.value = false
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
    ) {
        if (isLoading.value) {
            Column {
                CustomLoader()
            }
        } else {
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
            Text(
                text = stringResource(id = R.string.signup),
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
            if (err.value.isNotEmpty()) {
                Text(
                    text = "Please fill all the details",
                    fontFamily = FontFamily(Font(R.font.font_light)),
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(start = 15.dp, top = 10.dp)
                )
            }
            NormalTextInput(
                title = "User Name",
                value = userName.value,
                onValueChange = { userName.value = it },
                Error = ""
            )
            NormalTextInput(
                title = "Email",
                value = email.value,
                onValueChange = { email.value = it },
                Error = ""
            )
            PassWordInput(title = "password", value = passWord.value,
                Error = "",
                onValueChange = { passWord.value = it },
                onDone = {
                    validation()
                }
            )
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            Text(
                text = stringResource(id = R.string.terms),
                fontFamily = FontFamily(Font(R.font.font_medium)),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp),
                color = Color.Black
            )
            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )
            CustomButton(
                title = stringResource(id = R.string.signup),
                price = null,
                isShow = !isLoading.value,
                onClick = {
                    validation()
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
}

data class UserInfo(
    val userName: String,
    val emailId: String,
    val password: String,
)