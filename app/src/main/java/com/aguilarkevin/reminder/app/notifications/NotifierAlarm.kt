package com.aguilarkevin.reminder.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.app.activities.MainActivity
import com.aguilarkevin.reminder.database.AppDatabase
import com.aguilarkevin.reminder.database.Event

class NotifierAlarm : BroadcastReceiver() {

    private val GROUP_REMINDER_NOT = "com.aguilarkevin.reminder.WORK_REMINDER"
    private val CHANNEL_ID = "channel_01"

    override fun onReceive(context: Context, intent: Intent) {

        val reminder = Event(
            id = intent.getIntExtra("ID", 0),
            title = intent.getStringExtra("Title")!!,
            description = intent.getStringExtra("Description")!!,
            date = intent.getStringExtra("Date")!!,
            dateInMills = intent.getLongExtra("DateInMills", 0),
            type = intent.getStringExtra("Type")!!
        )

        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val intent1 = Intent(context, MainActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(MainActivity::class.java)
        taskStackBuilder.addNextIntent(intent1)

        val intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)

        var channel: NotificationChannel? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel =
                NotificationChannel(
                    CHANNEL_ID,
                    "reminder_channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(reminder.title)
            .setContentText(reminder.description)
            .setAutoCancel(true)
            .setSound(alarmSound)
            .setSmallIcon(R.drawable.ic_notifications_24px)
            .setContentIntent(intent2)
            .setGroup(GROUP_REMINDER_NOT)
            .setChannelId(CHANNEL_ID)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel!!)
        }

        notificationManager.notify(1, notification)

        Thread {
            AppDatabase.getDatabase(context).eventDao().deleteEvent(reminder)
        }.start()
    }
}