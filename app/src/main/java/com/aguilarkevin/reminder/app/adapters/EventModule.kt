package com.aguilarkevin.reminder.app.adapters

import android.content.Context
import android.widget.TextView
import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.app.adapters.eventHooks.SwipeEventHook
import com.aguilarkevin.reminder.app.models.EventItem
import com.idanatz.oneadapter.external.modules.ItemModule


class EventModule(private val context: Context) : ItemModule<EventItem>() {

    init {
        config {
            layoutResource = R.layout.event_item
        }
        onBind { model, viewBinder, metadata ->
            viewBinder.findViewById<TextView>(R.id.event_item_title).text = model.title
            viewBinder.findViewById<TextView>(R.id.event_item_desc).text = model.description
            viewBinder.findViewById<TextView>(R.id.event_item_date).text = model.date
        }
        onUnbind { model, viewBinder, metadata ->
            // unbind logic like stop animation, release webview resources, etc.
        }

        eventHooks += SwipeEventHook().getSwipeEventHook(context)
    }


}