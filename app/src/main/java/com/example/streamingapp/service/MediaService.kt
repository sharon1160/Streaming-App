package com.example.streamingapp.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.streamingapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class MediaService : MediaSessionService() {
    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var notificationManager: NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    @androidx.annotation.OptIn(UnstableApi::class)
    private fun startPlayerNotificationService(mainActivityIntent: Intent) {
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            Constants.REQUEST_CODE,
            mainActivityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        notificationManager.startPlayerNotificationService(this, pendingIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val mainActivityIntent: Intent = Intent(context, MainActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startPlayerNotificationService(mainActivityIntent)
        return super.onStartCommand(mainActivityIntent, flags, startId)
    }

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession = mediaSession

    private fun destroyPlayer(player: Player) {
        player.seekTo(Constants.INITIAL_POSITION)
        player.playWhenReady = false
        player.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        with(mediaSession) {
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                destroyPlayer(player)
            }
        }
    }
}
