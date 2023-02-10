package com.example.firstcomposeapp.screens.bottomNavigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.theme.PrimaryGreen

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Explore,
        BottomBarScreen.Cart,
        BottomBarScreen.Favorite,
        BottomBarScreen.Account,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier
                .height(60.dp)
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(
                text = screen.title,
                fontFamily = FontFamily(Font(R.font.font_bold)),
                fontSize = 12.sp,
                color =
                if (currentDestination?.hierarchy?.any { it.route == screen.route } == true)  PrimaryGreen
                else
                    Color.Black,

                )
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
                tint = if
                        (currentDestination?.hierarchy?.any { it.route == screen.route } == true)
                    PrimaryGreen
                else
                    Color.Black,
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = Color.Black.copy(alpha = ContentAlpha.medium),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        selectedContentColor = PrimaryGreen,
    )
}