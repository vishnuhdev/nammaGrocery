package com.example.firstcomposeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.firstcomposeapp.screens.bottomNavigation.BottomBarScreen
import com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.AccountScreen
import com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.cartScreen.CartScreen
import com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.exploreScreen.ExploreScreen
import com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.favoriteScreen.FavoriteScreen
import com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.home.DetailsScreen
import com.example.firstcomposeapp.screens.bottomNavigation.mainScreens.home.HomeScreen

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
            ExploreScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Cart.route) {
            CartScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen(navController = navController)
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
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")

    object DetailArgs {
        const val ProductData = "Productdata"
    }
}