package com.aguilarkevin.reminder.app.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.app.adapters.EmptinessModuleImpl
import com.aguilarkevin.reminder.app.adapters.EventModule
import com.aguilarkevin.reminder.app.adapters.SwipeModule
import com.aguilarkevin.reminder.app.models.EventItem
import com.aguilarkevin.reminder.app.notifications.NotifierAlarm
import com.aguilarkevin.reminder.database.Event
import com.aguilarkevin.reminder.database.EventViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.System.currentTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var oneAdapter: OneAdapter
    private lateinit var eventsViewModel: EventViewModel
    private val newEventActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        oneAdapter = OneAdapter(recycler_view) {
            this.itemModules += EventModule()
            //this.itemModules += SwipeModule()
            this.emptinessModule = EmptinessModuleImpl()
        }

        eventsViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        eventsViewModel.allEvents.observe(this, { events ->
            events.let {
                oneAdapter.setItems(this.toDiffableList(it))
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
            val dataList = ArrayList<String>()
            dataList.add(data?.getStringExtra("title")!!)
            dataList.add(data.getStringExtra("desc")!!)
            dataList.add(data.getStringExtra("date")!!)
            eventsViewModel.insert(Event(null, dataList[0], dataList[1], dataList[2], "Event"))

            val customTime = data.getIntExtra("timeInMillis", 0)
            val currentTime = currentTimeMillis()
            if (customTime > currentTime) {
                val dat = Data.Builder().build()
                val delay = customTime - currentTime
                scheduleNotification(delay, dat)
            }
        }
    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val notifyEvent = Intent( this@MainActivity, NotifierAlarm::class.java)
        intent.putExtra("ID", data.getInt("ID", 0))
    }

    private fun toDiffableList(events: List<Event>): List<Diffable> {
        val eventsList: ArrayList<Diffable> = ArrayList()
        for (i in events) {
            eventsList.add(EventItem(i.title, i.description, i.date, i.type))
        }
        return eventsList
    }

}