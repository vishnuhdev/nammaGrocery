@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.firstcomposeapp.screens.bottomNavigation.mainScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavController
import com.example.firstcomposeapp.NammaGroceryDB
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.components.CustomLoader
import com.example.firstcomposeapp.ui.theme.PrimaryGreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun AccountScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    val isLoading = rememberSaveable {
        mutableStateOf(false)
    }

    val dataBase = Firebase.database.reference
    val auth = Firebase.auth
    val userEmailId = Firebase.auth.currentUser?.email

    val userName = remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit){
        isLoading.value = true
        auth.uid?.let { it ->
            dataBase.child("AuthInfo").child(it).get().addOnSuccessListener {
                val authInfoMap = it.value as Map<*, *>
                userName.value = authInfoMap["userName"].toString()
                isLoading.value = false
            }.addOnFailureListener{
                isLoading.value = true
                Log.e("firebase", "Error getting data", it)
                isLoading.value = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
            .verticalScroll(state = scrollState)
            .background(color = Color.White)
    ) {
        Column {
            if (isLoading.value) {
                CustomLoader()
            } else {
                Column(
                    modifier = Modifier
                        .padding(start = 15.dp, top = 25.dp)
                        .background(color = Color.White)
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.avatar_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .height(85.dp)
                                .width(85.dp)
                                .clip(RoundedCornerShape(100.dp))
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 5.dp)
                        )
                        {
                            Text(
                                text = userName.value,
                                fontFamily = FontFamily(Font(R.font.font_bold)),
                                fontSize = 18.sp,
                            )
                            if (userEmailId != null) {
                                Text(
                                    text = userEmailId,
                                    fontFamily = FontFamily(Font(R.font.font_medium)),
                                    fontSize = 14.sp,
                                    color = Color.Gray.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.98f)
                        .padding(top = 25.dp, start = 20.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Widget(Image = R.drawable.mydetail, title = "My Details")
                    Widget(Image = R.drawable.order_icon, title = "Orders")
                    Widget(Image = R.drawable.loc, title = "Delivery Address")
                    Widget(Image = R.drawable.payment, title = "Payment Methods")
                    Widget(Image = R.drawable.promocode, title = "Promo Cord")
                    Widget(Image = R.drawable.bell, title = "Notifications ")
                    Widget(Image = R.drawable.help, title = "Help")
                    Widget(Image = R.drawable.about, title = "About ")
                }
                Card(
                    onClick = { /*TODO*/ },
                    colors = CardDefaults.cardColors(Color.Gray.copy(alpha = 0.1f)),
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth(0.9f)
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    TextButton(onClick = {
                        isLoading.value = true
                        GlobalScope.launch {
                            val items = NammaGroceryDB.getInstance()?.favoriteDao()?.getAll()!!
                            val cartItem = NammaGroceryDB.getInstance()?.cartDao()?.getAll()
                            NammaGroceryDB.getInstance()?.favoriteDao()?.deleteAll(items)
                            if (cartItem != null) {
                                NammaGroceryDB.getInstance()?.cartDao()?.deleteAll(cartItem)
                            }
                        }
                        Firebase.auth.signOut()
                        isLoading.value = false
                    },
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logout),
                            contentDescription = null,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            tint = PrimaryGreen
                        )
                        Text(
                            text = "Log Out",
                            color = PrimaryGreen,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.font_bold)),
                            modifier = Modifier
                                .padding(start = 15.dp)

                        )
                    }
                }
                Spacer(modifier = Modifier
                    .padding(bottom = 50.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Widget(
    Image: Int,
    title: String,
) {
    var painter = painterResource(Image)

    Card(
        onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                )
                Text(
                    text = title,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.KeyboardArrowRight, null)
            }

        }
    }
}

