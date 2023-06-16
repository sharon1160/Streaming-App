package com.example.streamingapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItem (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomBarItem(
        route = "home",
        title = "Home",
        icon =  Icons.Default.Home
    )

    object Player: BottomBarItem(
        route = "player",
        title = "Player",
        icon = Icons.Default.PlayArrow
    )
}
