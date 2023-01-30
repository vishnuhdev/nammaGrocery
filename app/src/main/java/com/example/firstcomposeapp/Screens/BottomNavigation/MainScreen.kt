package com.example.bottomnavbardemo.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.firstcomposeapp.Screens.BottomNavigation.BottomBar
import com.example.nestednavigationbottombardemo.graphs.HomeNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        HomeNavGraph(navController = navController)
    }
}