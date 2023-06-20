package com.example.streamingapp.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.streamingapp.ui.home.HomeScreen
import com.example.streamingapp.ui.navigation.BottomBarItem
import com.example.streamingapp.ui.player.PlayerScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination =  BottomBarItem.Home.route
    ) {
        composable(route = BottomBarItem.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarItem.Player.route) {
            PlayerScreen()
        }
    }
}
