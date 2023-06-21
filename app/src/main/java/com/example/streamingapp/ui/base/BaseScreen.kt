package com.example.streamingapp.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.streamingapp.ui.navigation.graphs.HomeNavGraph
import com.example.streamingapp.ui.theme.StreamingAppTheme

@Composable
fun BaseScreen() {
    BaseScreenContent()
}

@Composable
fun BaseScreenContent() {
    val navController: NavHostController = rememberNavController()
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            HomeNavGraph(navController = navController)
        }
    }
}

@Preview
@Composable
fun BaseScreenPreview() {
    StreamingAppTheme {
        BaseScreenContent()
    }
}
