package com.example.streamingapp.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val player: ExoPlayer
) {
    private var notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init { createNotificationChannel() }

    @UnstableApi
    fun startNotificationService(
        mediaSessionService: MediaSessionService,
        pendingIntent: PendingIntent
    ) {
        createNotification(pendingIntent)
        startNotification(mediaSessionService)
    }

    @UnstableApi
    private fun createNotification(pendingIntent: PendingIntent) {
        PlayerNotificationManager.Builder(context, ID, CHANNEL_ID)
            .setMediaDescriptionAdapter(NotificationAdapter(context, pendingIntent)).build()
            .also { notificationManager ->
                notificationManager.setUseFastForwardActionInCompactView(true)
                notificationManager.setUseRewindActionInCompactView(true)
                notificationManager.setUseNextActionInCompactView(false)
                notificationManager.setUsePreviousActionInCompactView(false)
                notificationManager.setPriority(NotificationCompat.PRIORITY_LOW)
                notificationManager.setPlayer(player)
            }
    }

    private fun startNotification(mediaSessionService: MediaSessionService) {
        val notification = Notification.Builder(context, CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE).build()
        mediaSessionService.startForeground(ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val ID = 1
        private const val CHANNEL_NAME = "my_channel"
        private const val CHANNEL_ID = "my_channel_01"
    }
}
