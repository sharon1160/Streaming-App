package com.example.streamingapp.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.streamingapp.ui.theme.StreamingAppTheme

@Composable
fun HomeScreen() {
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
