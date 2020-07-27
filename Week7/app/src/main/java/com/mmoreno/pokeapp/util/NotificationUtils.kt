package com.mmoreno.pokeapp.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import com.mmoreno.pokeapp.R

private val NOTIFICATION_ID = 0

/**
 * Custom method for creating and sending the notification
 * @appContext instance of context for accessing the resources
 * @messageBody [String] text to be displayed in the body of the notification
 */
fun sendDataSyncNotification(context: Context) {

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(
                context.getString(R.string.notificationChannelId), context.getString(
                    R.string.channelDescription
                ), NotificationManager.IMPORTANCE_HIGH
            )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationManager.createNotificationChannel(notificationChannel)

        val builder =
            Notification.Builder(context, context.getString(R.string.notificationChannelId))
                .setContentTitle(context.getString(R.string.notificationTitle))
                .setContentText(context.getString(R.string.textSyncDataNotification))
                .setSmallIcon(R.drawable.ic_stat_name)
        notificationManager.notify(1234, builder.build())
    }
}
