package com.aguilarkevin.reminder.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.room.Room
import com.aguilarkevin.reminder.app.activities.MainActivity
import com.aguilarkevin.reminder.database.AppDatabase
import com.aguilarkevin.reminder.database.Event
import android.app.Notification
import com.aguilarkevin.reminder.R

class NotifierAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val db = Room.databaseBuilder(
            context!!.applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        if (intent != null) {
            db.eventDao().deleteEvent(
                Event(
                    intent?.getIntExtra("ID", 0),
                    intent.getStringExtra("Title")!!,
                    intent.getStringExtra("Description")!!,
                    intent.getStringExtra("Date")!!,
                    intent.getStringExtra("Type")!!
                )
            )
        }
        db.close()

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val intent1 = Intent(context, MainActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(MainActivity::class.java)
        taskStackBuilder.addNextIntent(intent1)

        val intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context)

        var channel: NotificationChannel? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel =
                NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH)
        }

        val notification: Notification = builder.setContentTitle("Reminder")
            .setContentText(intent!!.getStringExtra("Title")).setAutoCancel(true)
            .setSound(alarmSound).setSmallIcon(R.drawable.ic_notifications_24px)
            .setContentIntent(intent2)
            .setChannelId("my_channel_01")
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel!!)
        }
        notificationManager.notify(1, notification)

    }
}