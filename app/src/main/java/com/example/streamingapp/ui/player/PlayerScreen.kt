package com.example.streamingapp.ui.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.streamingapp.R
import com.example.streamingapp.data.model.DetailedSound
import com.example.streamingapp.ui.home.Message
import com.example.streamingapp.ui.theme.Roboto
import com.example.streamingapp.ui.theme.StreamingAppTheme


@UnstableApi
@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel = hiltViewModel(),
    navController: NavHostController,
    id: Int?
) {

    val uiState by playerViewModel.uiState.collectAsState()
    val navigationToBack = { navController.popBackStack() }

    id?.let { soundId ->
        playerViewModel.searchSoundById(soundId)
    }

    StreamingAppTheme {
        PlayerScreenContent(
            navigationToBack,
            uiState.currentSound,
            playerViewModel::updateIsPlaying,
            uiState.isPlaying,
            playerViewModel::playPauseSound,
            playerViewModel::fastForward,
            playerViewModel::rewind,
            uiState.currentPosition
        )
    }
}

@Composable
fun PlayerScreenContent(
    navigationToBack: () -> Boolean,
    currentSound: DetailedSound?,
    updateIsPlaying: (Boolean) -> Unit,
    isPlaying: Boolean,
    playPauseSound: () -> Unit,
    fastForward:() -> Unit,
    rewind:() -> Unit,
    currentPosition: Long
) {
    Scaffold {
        if (currentSound != null) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top
                ) {
                    IconButton(
                        onClick = {
                            navigationToBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.arrow_back_content_description),
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(horizontal = 45.dp, vertical = 100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(currentSound.images["waveform_l"])
                            .crossfade(true)
                            .scale(Scale.FILL)
                            .build(),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .height(300.dp)
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 64.dp),
                    ) {
                        Text(
                            text = currentSound.name,
                            fontFamily = Roboto,
                            style = MaterialTheme.typography.bodyLarge,
                        )

                        Text(
                            text = currentSound.username,
                            fontFamily = Roboto,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    PlayerSlider(currentPosition, currentSound.duration)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        IconButton(
                            modifier = Modifier.size(50.dp),
                            onClick = {
                                rewind()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.FastRewind,
                                contentDescription = stringResource(
                                    R.string.fast_rewind_icon_content_description
                                ),
                                modifier = Modifier.fillMaxSize(),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(
                            modifier = Modifier.size(60.dp),
                            onClick = {
                                playPauseSound()
                                updateIsPlaying(!isPlaying)
                            }
                        ) {
                            val icon = if(isPlaying) {
                                Icons.Default.PauseCircle
                            } else {
                                Icons.Default.PlayCircle
                            }
                            Icon(
                                imageVector = icon,
                                contentDescription = stringResource(
                                    R.string.play_pause_icon_content_description
                                ),
                                modifier = Modifier.fillMaxSize(),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(
                            modifier = Modifier.size(50.dp),
                            onClick = {
                                fastForward()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.FastForward,
                                contentDescription = stringResource(
                                    R.string.fast_forward_icon_content_description
                                ),
                                modifier = Modifier.fillMaxSize(),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        } else {
            Message(text = stringResource(R.string.loading_message))
        }
    }
}

@Composable
fun PlayerSlider(currentPosition: Long, duration: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            value = currentPosition.toFloat(),
            onValueChange = {},
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTickColor = MaterialTheme.colorScheme.primary
            ),
            valueRange = 0f..(duration*1000)
        )
    }
}

@Preview
@Composable
fun PlayerScreenPreview() {
    StreamingAppTheme {
        PlayerScreenContent({ false }, null, {}, false, {}, {}, {}, 0)
    }
}
