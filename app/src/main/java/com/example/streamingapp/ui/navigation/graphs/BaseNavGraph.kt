package com.example.streamingapp.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.streamingapp.ui.home.HomeScreen
import com.example.streamingapp.ui.player.PlayerScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination =  "home"
    ) {
        composable(route = "home") {
            HomeScreen(navController = navController)
        }
        composable(
            route = "player/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id: Int? = backStackEntry.arguments?.getInt("id")
            PlayerScreen(navController = navController, id = id)
        }
    }
}
