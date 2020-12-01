package com.aguilarkevin.reminder.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EventsRepository
    val allEvents: LiveData<List<Event>>

    init {
        val eventDao = AppDatabase.getDatabase(application).eventDao()
        repository = EventsRepository(eventDao)
        allEvents = repository.allEvents
    }

    fun insert(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(event)
    }

    fun delete(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(event)
    }
}