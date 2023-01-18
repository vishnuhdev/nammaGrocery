package com.example.firstcomposeapp.Screens.BottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector



sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Home : BottomBarScreen(
        route = "HOME",
        title = "Home",
        icon = Icons.Outlined.Home
    )

    object Explore : BottomBarScreen(
        route = "EXPLORE",
        title = "Explore",
        icon = Icons.Outlined.Search
    )

    object Cart : BottomBarScreen(
        route = "CART",
        title = "Cart",
        icon = Icons.Outlined.ShoppingCart
    )

    object Favorite : BottomBarScreen(
        route = "FAVORITE",
        title = "Favorite",
        icon = Icons.Outlined.FavoriteBorder
    )
    object Account : BottomBarScreen(
        route = "ACCOUNT",
        title = "Account",
        icon = Icons.Outlined.AccountCircle
    )
}