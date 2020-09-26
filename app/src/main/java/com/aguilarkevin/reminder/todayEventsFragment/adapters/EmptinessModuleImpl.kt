package com.aguilarkevin.reminder.todayEventsFragment.adapters

import com.aguilarkevin.reminder.R
import com.idanatz.oneadapter.external.modules.EmptinessModule

class EmptinessModuleImpl : EmptinessModule() {
    init {
        config {
            layoutResource = R.layout.today_events_empty_state
        }
        onBind { viewBinder, metadata -> }
        onUnbind { viewBinder, metadata -> }
    }
}