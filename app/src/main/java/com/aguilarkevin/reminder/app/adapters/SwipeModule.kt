package com.aguilarkevin.reminder.app.adapters

import com.aguilarkevin.reminder.app.models.EventItem
import com.idanatz.oneadapter.external.event_hooks.SwipeEventHook
import com.idanatz.oneadapter.external.modules.ItemModule

class SwipeModule: ItemModule<EventItem>() {
    init {
        // config, onBind, etc...
        eventHooks += SwipeEventHook<EventItem>().apply {
            config {
                swipeDirection = listOf(SwipeEventHook.SwipeDirection.Start, SwipeEventHook.SwipeDirection.End)
            }
            onSwipe { canvas, xAxisOffset, viewBinder ->
                canvas.drawColor(16716306)
                // draw your swipe UI here.
                // like painting the canvas red with a delete icon.
            }
            onSwipeComplete { model, viewBinder, metadata ->
                // place your swipe logic here.
                // like removing an item after it was swiped right.
            }
        }
    }
}