package com.example.firstcomposeapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.firstcomposeapp.Screen
import com.example.firstcomposeapp.screens.authScreens.LoginWithEmail
import com.example.firstcomposeapp.screens.authScreens.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Screen.LoginWithEmail.route
    ) {
        composable(route = Screen.LoginWithEmail.route) {
            LoginWithEmail(navController = navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}