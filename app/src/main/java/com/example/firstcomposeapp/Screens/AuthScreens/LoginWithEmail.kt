@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.firstcomposeapp.screens.authScreens


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.firstcomposeapp.navigation.Graph
import com.example.firstcomposeapp.ui.theme.PrimaryGreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginWithEmail(navController: NavController) {
    val mContext = LocalContext.current
    var scrollState = rememberScrollState()

    val auth = Firebase.auth

    var isLoading = rememberSaveable {
        mutableStateOf(false)
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
        if (email.value.isEmpty() and passWord.value.isNotEmpty()) {
            err.value = "U"
        }
        if (passWord.value.isEmpty() and email.value.isNotEmpty()) {
            Toast.makeText(mContext, "Password is Empty", Toast.LENGTH_SHORT).show()
        }
        if (email.value.isEmpty() and passWord.value.isEmpty()) {
            err.value = "This Field is Mandatory"
        }
        if (email.value.isNotEmpty() and passWord.value.isNotEmpty()) {
            isLoading.value = true
            auth.signInWithEmailAndPassword(email.value.trim(), passWord.value.trim())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("AUTH", "signInWithEmail:success")
                        isLoading.value = false
                        navController.navigate(Graph.HOME)
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
    )
    {
        if (isLoading.value) {
            Column{
                CustomLoader()
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .height(85.dp)
                    .width(85.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Text(text = "Loging",
                fontFamily = FontFamily(Font(R.font.font_bold)),
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(start = 15.dp, top = 35.dp)
            )
            Text(
                text = stringResource(id = R.string.emailandpass),
                fontFamily = FontFamily(Font(R.font.font_light)),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp)
            )
//        Text(
//            text = stringResource(id = R.string.emailandpass ),
//            fontFamily = FontFamily(Font(R.font.font_light)),
//            fontSize = 12.sp,
//            modifier = Modifier
//                .padding(start = 15.dp, top = 10.dp),
//            color = Color.Red
//        )
            NormalTextInput(
                title = "Email",
                value = email.value,
                onValueChange = { email.value = it },
                Error = err.value
            )
            PassWordInput(title = "password", value = passWord.value,
                Error = err.value,
                onValueChange = { passWord.value = it }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {},
                ) {
                    Text(
                        text = stringResource(id = R.string.forgotPass),
                        fontFamily = FontFamily(Font(R.font.font_bold)),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier
                .height(10.dp))
            CustomButton(title = stringResource(id = R.string.login),
                onClick = {
                    validation()
                },
                isShow = !isLoading.value
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(Screen.SignUp.route)
                    },
                ) {
                    Text(
                        text = stringResource(id = R.string.donthaveacc),
                        fontFamily = FontFamily(Font(R.font.font_bold)),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(id = R.string.signup),
                        fontFamily = FontFamily(Font(R.font.font_bold)),
                        fontSize = 14.sp,
                        color = PrimaryGreen
                    )
                }
            }
        }
    }
}