package com.example.firstcomposeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottomnavbardemo.screens.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Root(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    var currentUser = auth.currentUser
    val startDestination = if (currentUser != null) {
        Graph.HOME
    } else {
        Graph.AUTHENTICATION
    }
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDestination,
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen()
        }
    }
    val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val user = firebaseAuth.currentUser
        if (user != null) {
            navController.navigate(Graph.HOME)
        } else {
            navController.navigate(Graph.AUTHENTICATION)
        }
    }
    auth.addAuthStateListener(authListener)
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}