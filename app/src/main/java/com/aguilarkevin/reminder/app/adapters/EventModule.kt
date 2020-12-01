package com.aguilarkevin.reminder.app.adapters

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.app.models.EventItem
import com.idanatz.oneadapter.external.event_hooks.SwipeEventHook
import com.idanatz.oneadapter.external.modules.ItemModule


class EventModule(private val context: Context) : ItemModule<EventItem>() {

    private val ICON_MARGIN = 50


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

        eventHooks += SwipeEventHook<EventItem>().apply {
            config {
                swipeDirection = listOf(
                    SwipeEventHook.SwipeDirection.Start,
                    SwipeEventHook.SwipeDirection.End
                )
            }
            onSwipe { canvas, xAxisOffset, viewBinder ->
                paintSwipeArea(
                    canvas,
                    xAxisOffset,
                    viewBinder.rootView
                )
            }

            onSwipeComplete { model, viewBinder, metadata ->
                // place your swipe logic here.
                // like removing an item after it was swiped right.
            }
        }
    }

    private fun paintSwipeArea(canvas: Canvas, xAxisOffset: Float, rootView: View) {
        if (xAxisOffset < 0 || xAxisOffset > 0) {
            val color = ContextCompat.getColor(this.context, R.color.accent2)

            val paint = Paint()
            paint.style = Paint.Style.FILL
            paint.color = color
            paint.isAntiAlias = true

            val rectF = RectF(
                rootView.left.toFloat(), rootView.top.toFloat(),
                rootView.right.toFloat(), rootView.bottom.toFloat()
            )

            canvas.drawRoundRect(rectF, 25f, 25f, paint)
            setIcon(xAxisOffset, rootView)?.draw(canvas)
        }
    }

    private fun setIcon(xAxisOffset: Float, rootView: View): Drawable? {
        return when {
            (xAxisOffset < 0) ->
                getIconLeft(
                    icon = ContextCompat.getDrawable(this.context, R.drawable.ic_edit_24px)!!,
                    rootView = rootView
                )

            (xAxisOffset > 0) ->
                getIconRight(
                    icon = ContextCompat.getDrawable(this.context, R.drawable.ic_done_24px)!!,
                    rootView = rootView
                )
            else -> null
        }
    }


    //methods to set icon bounds

    //in case swipe rigth
    private fun getIconRight(icon: Drawable, rootView: View): Drawable {
        icon.let {
            val middle = rootView.bottom - rootView.top

            val top = rootView.top + (middle / 2) - (it.intrinsicHeight / 2)
            val bottom = top + it.intrinsicHeight
            val left = rootView.left + ICON_MARGIN
            val right = left + it.intrinsicWidth
            it.setBounds(left, top, right, bottom)

            return it
        }
    }

    //in case swipe left
    private fun getIconLeft(icon: Drawable, rootView: View): Drawable {
        icon.let {
            val middle = rootView.bottom - rootView.top
            val top = rootView.top + (middle / 2) - (it.intrinsicHeight / 2)
            val bottom = top + it.intrinsicHeight
            val right = rootView.right - ICON_MARGIN
            val left = right - it.intrinsicWidth
            it.setBounds(left, top, right, bottom)

            return it
        }
    }
}