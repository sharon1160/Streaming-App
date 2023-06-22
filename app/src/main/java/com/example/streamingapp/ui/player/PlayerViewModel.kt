package com.example.streamingapp.ui.player

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.streamingapp.data.model.DetailedSound
import com.example.streamingapp.domain.usecase.GetSoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getSoundUseCase: GetSoundUseCase,
    private val player: ExoPlayer
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState = _uiState.asStateFlow()

    private var updateSeekBarJob: Job? = null

    private fun updateSlider() = viewModelScope.launch {
        while (true) {
            _uiState.update {
                it.copy(currentPosition = player.currentPosition)
            }
            delay(1000)
        }
    }

    @UnstableApi
    fun loadSound(sound: DetailedSound) {
        val mediaItem = MediaItem.Builder()
            .setUri(sound.previews["preview-hq-mp3"])
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                    .setArtworkUri(Uri.parse(sound.images["waveform_l"]))
                    .setAlbumTitle(sound.name)
                    .setDisplayTitle(sound.username)
                    .build()
            )
            .build()
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    fun playPauseSound() {
        updateSeekBarJob = if (player.isPlaying) {
            player.pause()
            updateSeekBarJob?.cancel()
            null
        } else {
            player.play()
            updateSeekBarJob?.cancel()
            updateSlider()
        }
    }

    fun searchSoundById(id: Int) {
        viewModelScope.launch {
            val selectedSound = getSoundUseCase.invoke(id = id.toString())
            _uiState.update {
                it.copy(currentSound = getSoundUseCase.invoke(id = id.toString()))
            }
            selectedSound?.let { loadSound(it) }
        }
    }

    fun updateIsPlaying(newValue: Boolean) {
        _uiState.update {
            it.copy(isPlaying = newValue)
        }
    }

    fun fastForward() {
        val forwardTo = player.currentPosition + DEFAULT_FORWARD
        if (forwardTo > player.duration) {
            seekTo(player.duration)
        } else {
            seekTo(forwardTo)
        }
    }

    fun rewind() {
        val rewindTo = player.currentPosition - DEFAULT_REWIND
        if (rewindTo < 0) {
            seekTo(0)
        } else {
            seekTo(rewindTo)
        }
    }

    private fun seekTo(position: Long) {
        player.seekTo(position)
    }

    companion object {
        const val DEFAULT_FORWARD = 15 * 1000
        const val DEFAULT_REWIND = 15 * 1000
    }
}
