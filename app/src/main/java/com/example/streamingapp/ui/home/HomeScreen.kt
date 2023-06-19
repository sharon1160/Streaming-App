package com.example.streamingapp.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streamingapp.ui.theme.StreamingAppTheme

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    homeViewModel.searchAllSounds("guitarra")
    homeViewModel.searchSoundById(516693)
    StreamingAppTheme {
        HomeScreenContent()
    }
}

@Composable
fun HomeScreenContent() {
    Text(text = "Home")
}

@Preview
@Composable
fun HomeScreenPreview() {
  StreamingAppTheme {
      HomeScreenContent()
  }  
}
