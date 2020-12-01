package com.aguilarkevin.reminder.database

import androidx.lifecycle.LiveData

class EventsRepository(private val eventDao: EventDao) {

    val allEvents: LiveData<List<Event>> = eventDao.getAllEvents()

    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    suspend fun delete(event: Event){
        eventDao.deleteEvent(event)
    }
}