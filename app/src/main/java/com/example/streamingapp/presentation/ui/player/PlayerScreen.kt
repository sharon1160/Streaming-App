package com.example.streamingapp.presentation.ui.player

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.streamingapp.presentation.ui.theme.StreamingAppTheme

@Composable
fun PlayerScreen() {
    StreamingAppTheme {
        PlayerScreenContent()
    }
}

@Composable
fun PlayerScreenContent() {
    Text(text = "Player")
}

@Preview
@Composable
fun PlayerScreenPreview() {
    StreamingAppTheme {
        PlayerScreenContent()
    }
}
