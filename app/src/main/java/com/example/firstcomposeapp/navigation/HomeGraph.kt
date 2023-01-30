package com.example.nestednavigationbottombardemo.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.firstcomposeapp.Screens.BottomNavigation.BottomBarScreen
import com.example.firstcomposeapp.Screens.BottomNavigation.MainScreens.AccountScreen
import com.example.firstcomposeapp.Screens.BottomNavigation.MainScreens.Home.DetailsScreen
import com.example.firstcomposeapp.Screens.BottomNavigation.MainScreens.HomeScreen
import com.example.firstcomposeapp.Screens.ScreenContent
import com.example.firstcomposeapp.navigation.Graph

@Composable
fun HomeNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Explore.route) {
            ScreenContent(
                name = BottomBarScreen.Explore.route,
                onClick = { }
            )
        }
        composable(route = BottomBarScreen.Cart.route) {
            ScreenContent(
                name = BottomBarScreen.Cart.route,
                onClick = { }
            )
        }
        composable(route = BottomBarScreen.Favorite.route) {
            ScreenContent(
                name = BottomBarScreen.Favorite.route,
                onClick = { }
            )
        }
        composable(route = BottomBarScreen.Account.route) {
            AccountScreen(navController = rememberNavController())
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(
            route = DetailsScreen.Information.route) {
            DetailsScreen(navController = navController)
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")

    object DetailArgs {
        const val ProductData = "Productdata"
    }
}