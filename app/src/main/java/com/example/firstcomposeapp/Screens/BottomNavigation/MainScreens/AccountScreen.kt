package com.example.firstcomposeapp.Screens.BottomNavigation.MainScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AccountScreen(navController: NavController){
    val scrollState = rememberScrollState()

    val isLoading = rememberSaveable{
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(state = scrollState)
    ){
        if (isLoading.value){
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.Black,
                trackColor = Color.White
            )
        }else{
            null
        }
        TextButton(onClick = {
            isLoading.value = true
            Firebase.auth.signOut()
            isLoading.value = false
        }) {
            Text(text = "Sign Out")
        }
    }
}


