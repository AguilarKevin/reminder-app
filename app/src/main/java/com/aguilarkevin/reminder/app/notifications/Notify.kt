package com.aguilarkevin.reminder.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.app.activities.MainActivity

object Notify {

    fun notifyEvent(title: String, description: String, context: Context) {
        val intent = Intent(context.applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val notificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        val notification = createNotification(context, title, description)
        notification.priority = NotificationCompat.PRIORITY_MAX
        createChannel(notification, notificationManager)
        notificationManager.notify(1000, notification.build())
    }


    private fun createChannel(
        notification: NotificationCompat.Builder,
        notificationManager: NotificationManager
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(NotificationUtils.NOTIFICATION_CHANNEL)

            val ringtoneManager =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes =
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

            val channel = NotificationChannel(
                NotificationUtils.NOTIFICATION_CHANNEL,
                NotificationUtils.NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.setSound(ringtoneManager, audioAttributes)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(
                100,
                200,
                300,
                400,
                500,
                400,
                300,
                200,
                400,
                100,
                200,
                300,
                400,
                500,
                400,
                300,
                200,
                400
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(
        context: Context,
        title: String,
        description: String
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            context.applicationContext,
            NotificationUtils.NOTIFICATION_CHANNEL
        )
            .setSmallIcon(R.drawable.ic_notifications_24px)
            .setContentTitle(title)
            .setContentText(description)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
    }
}