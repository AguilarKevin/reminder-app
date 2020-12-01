package com.aguilarkevin.reminder.app.models

import com.idanatz.oneadapter.external.interfaces.Diffable

class EventItem(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val type: String
) : Diffable {

    override val uniqueIdentifier: Long
        get() = this.id.toLong()

    override fun areContentTheSame(other: Any): Boolean {
        TODO("Not yet implemented")
    }

}