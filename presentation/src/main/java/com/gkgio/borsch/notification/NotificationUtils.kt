package com.gkgio.borsch.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.getColorCompat

class NotificationUtils(val context: Context) : ContextWrapper(context) {

    companion object {
        const val DEFAULT_CHANNEL_ID = "Daily"
        const val DEFAULT_CHANNEL_NAME = "Daily"
        const val DEFAULT_CHANNEL_DESCRIPTION = "Daily borsch notification"
    }

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        initChannels()
    }

    private fun initChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel =
                NotificationChannel(DEFAULT_CHANNEL_ID, DEFAULT_CHANNEL_NAME, importance)
            notificationChannel.description = DEFAULT_CHANNEL_DESCRIPTION
            // Register the channel with the system
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun buildNotification(title: String?, message: String?, pendingIntent: PendingIntent, id: Int) {
        val builder = NotificationCompat.Builder(this, DEFAULT_CHANNEL_ID)
            .setLargeIcon(
                BitmapFactory.decodeResource(resources, R.drawable.icon_notificaion_large)
            )
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.ic_push_notification)
            builder.color = context.getColorCompat(R.color.accent)
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher)
        }

        notificationManager.notify(id, builder.build())
    }
}