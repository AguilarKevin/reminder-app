package com.aguilarkevin.reminder.app.adapters

import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.app.models.EventItem
import com.idanatz.oneadapter.external.modules.ItemModule

class EventModule : ItemModule<EventItem>() {
    init {
        config {
            layoutResource = R.layout.event_item
        }
        onBind { model, viewBinder, metadata ->

        }
        onUnbind { model, viewBinder, metadata ->
            // unbind logic like stop animation, release webview resources, etc.
        }
    }
}