package com.aguilarkevin.reminder.app.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.aguilarkevin.reminder.database.AppDatabase
import com.aguilarkevin.reminder.database.Event

class EventsManager {

    //func to program notificacion for the next event
    fun updatePendingReminders(context: Context) {
        Thread {
            val eventDao = AppDatabase.getDatabase(context).eventDao()
            if (eventDao.getEventCount() > 0) {
                scheduleNotification(
                    context = context,
                    event = eventDao.getNextEvent()
                )
            }
        }.start()
    }

    private fun scheduleNotification(context: Context, event: Event) {

        val notifyEvent = Intent(context, ReminderBroadcastReceiver::class.java)
        notifyEvent.putExtra("ID", event.id)
        notifyEvent.putExtra("Title", event.title)
        notifyEvent.putExtra("Description", event.description)
        notifyEvent.putExtra("Date", event.date)
        notifyEvent.putExtra("DateInMills", event.dateInMills)
        notifyEvent.putExtra("Type", event.type)

        val intent1 = PendingIntent.getBroadcast(
            context,
            event.id,
            notifyEvent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, event.dateInMills, intent1)
    }
}