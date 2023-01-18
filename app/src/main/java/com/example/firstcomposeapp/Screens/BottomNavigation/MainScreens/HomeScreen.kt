package com.example.firstcomposeapp.Screens.BottomNavigation.MainScreens

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.firstcomposeapp.navigation.Graph

@Composable
fun HomeScreen(navController: NavHostController){
    TextButton(
        onClick = { navController.navigate(Graph.DETAILS)  }
    )
    {
        Text(text = "Click")

    }
}