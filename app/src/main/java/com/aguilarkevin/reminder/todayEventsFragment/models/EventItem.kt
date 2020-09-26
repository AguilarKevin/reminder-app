package com.aguilarkevin.reminder.todayEventsFragment.models

import com.idanatz.oneadapter.external.interfaces.Diffable

class EventItem : Diffable {
    override val uniqueIdentifier: Long
        get() = (Math.random() * 100).toLong()

    override fun areContentTheSame(other: Any): Boolean {
        TODO("Not yet implemented")
    }

}