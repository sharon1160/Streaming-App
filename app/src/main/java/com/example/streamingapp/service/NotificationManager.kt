package com.example.streamingapp.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.streamingapp.R
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

    @androidx.annotation.OptIn(UnstableApi::class)
    fun startPlayerNotificationService(
        mediaSessionService: MediaSessionService,
        pendingIntent: PendingIntent
    ) {
        val notificationManager = PlayerNotificationManager
            .Builder(
                context,
                Constants.ID,
                Constants.CHANNEL_ID
            )
            .setMediaDescriptionAdapter(NotificationAdapter(context, pendingIntent)).build()

        setMediaPlayerControlButtons(notificationManager)

        val notification = Notification.Builder(context, Constants.CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE).build()

        mediaSessionService.startForeground(Constants.ID, notification)
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    private fun setMediaPlayerControlButtons(notificationManager: PlayerNotificationManager) {
        notificationManager.setUseNextActionInCompactView(false)
        notificationManager.setUsePreviousActionInCompactView(false)
        notificationManager.setUseRewindActionInCompactView(true)
        notificationManager.setUseFastForwardActionInCompactView(true)
        notificationManager.setPriority(NotificationCompat.PRIORITY_LOW)
        notificationManager.setPlayer(player)
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            Constants.CHANNEL_ID,
            Constants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }
}

@UnstableApi
class NotificationAdapter(
    private val context: Context,
    private val pendingIntent: PendingIntent?
) : PlayerNotificationManager.MediaDescriptionAdapter {

    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        return pendingIntent
    }
    override fun getCurrentContentTitle(player: Player): CharSequence {
        return player.mediaMetadata.albumTitle.toString()
    }
    override fun getCurrentContentText(player: Player): CharSequence {
        return player.mediaMetadata.displayTitle.toString()
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        Glide.with(context)
            .asBitmap()
            .load(player.mediaMetadata.artworkUri)
            .placeholder(R.drawable.music_placeholder)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.onBitmap(resource)
                }
            })
        return null
    }
}
