package com.aguilarkevin.reminder.app.models

import com.idanatz.oneadapter.external.interfaces.Diffable
import java.util.*

class EventItem(title: String, desc: String, date: String, type: String) : Diffable {

    val title = title
    val description = desc
    val date = date
    val type = type

    override val uniqueIdentifier: Long
        get() = (Math.random() * 100).toLong()

    override fun areContentTheSame(other: Any): Boolean {
        TODO("Not yet implemented")
    }

}