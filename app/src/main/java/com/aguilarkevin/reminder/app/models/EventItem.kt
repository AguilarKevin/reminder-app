package com.aguilarkevin.reminder.app.models

import com.idanatz.oneadapter.external.interfaces.Diffable

class EventItem(title: String, desc: String, date: String, color: Int) : Diffable {

    val title = title
    val description = desc
    val date = date
    val color = color

    override val uniqueIdentifier: Long
        get() = (Math.random() * 100).toLong()

    override fun areContentTheSame(other: Any): Boolean {
        TODO("Not yet implemented")
    }

}