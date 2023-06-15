package com.example.streamingapp.presentation.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.streamingapp.presentation.ui.home.HomeScreen
import com.example.streamingapp.presentation.ui.navigation.BottomBarItem
import com.example.streamingapp.presentation.ui.player.PlayerScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination =  BottomBarItem.Home.route
    ) {
        composable(route = BottomBarItem.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarItem.Player.route) {
            PlayerScreen()
        }
    }
}
