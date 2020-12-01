package com.aguilarkevin.reminder.app.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.app.adapters.EmptinessModuleImpl
import com.aguilarkevin.reminder.app.adapters.EventModule
import com.aguilarkevin.reminder.app.models.EventItem
import com.aguilarkevin.reminder.app.notifications.EventsManager
import com.aguilarkevin.reminder.database.Event
import com.aguilarkevin.reminder.database.EventViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.calendar_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var oneAdapter: OneAdapter
    private lateinit var eventsViewModel: EventViewModel
    private val newEventActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        oneAdapter = OneAdapter(recycler_view) {
            this.itemModules += EventModule(applicationContext)
            this.emptinessModule = EmptinessModuleImpl()
        }

        eventsViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        //observer who handles the changes in the data
        eventsViewModel.allEvents.observe(this, { events ->
            oneAdapter.setItems(this.toDiffableList(events))
            if (events.isNotEmpty()) {
                EventsManager().updatePendingReminders(this)
            }
        })

        findViewById<ExtendedFloatingActionButton>(R.id.fab).setOnClickListener {
            startActivityForResult(
                Intent(this@MainActivity, NewEventActivity::class.java),
                newEventActivityRequestCode
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newEventActivityRequestCode && resultCode == Activity.RESULT_OK) {

             var newEvent = Event(
                id = eventsViewModel.allEvents.value!!.size,
                title = data!!.getStringExtra("Title")!!,
                description = data.getStringExtra("Description")!!,
                date = data.getStringExtra("Date")!!,
                dateInMills = data.getLongExtra("timeInMillis", 0),
                type = data.getStringExtra("Type")!!
            )
            eventsViewModel.insert(newEvent)
        }
    }


    private fun toDiffableList(events: List<Event>): List<Diffable> {
        val eventsList: ArrayList<Diffable> = ArrayList()
        events.forEach {
            eventsList.add(
                EventItem(
                    it.id,
                    it.title,
                    it.description,
                    it.date,
                    it.type
                )
            )
        }
        return eventsList
    }

}