package com.aguilarkevin.reminder.app.adapters.eventHooks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.app.models.EventItem
import com.aguilarkevin.reminder.app.utils.AlertDialog
import com.idanatz.oneadapter.external.event_hooks.SwipeEventHook

class SwipeEventHook {

    private val ICON_MARGIN = 50

    fun getSwipeEventHook(context: Context): SwipeEventHook<EventItem> {
        return SwipeEventHook<EventItem>().apply {
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
                    viewBinder.rootView,
                    context
                )
            }

            onSwipeComplete { model, viewBinder, metadata ->
                when (metadata.swipeDirection) {
                    SwipeEventHook.SwipeDirection.Start -> {
                        AlertDialog().newAlertDialog(
                            context = context,
                            title = "xd",
                            message = "xd"
                        )
                    }
                    SwipeEventHook.SwipeDirection.End -> {
                    }
                    else -> Log.e("x", "d")
                }
            }
        }
    }

    private fun paintSwipeArea(
        canvas: Canvas,
        xAxisOffset: Float,
        rootView: View,
        context: Context
    ) {
        if (xAxisOffset < 0 || xAxisOffset > 0) {
            val color = ContextCompat.getColor(context, R.color.accent2)

            val paint = Paint()
            paint.style = Paint.Style.FILL
            paint.color = color
            paint.isAntiAlias = true

            val rectF = RectF(
                rootView.left.toFloat(), rootView.top.toFloat(),
                rootView.right.toFloat(), rootView.bottom.toFloat()
            )

            canvas.drawRoundRect(rectF, 25f, 25f, paint)
            setIcon(xAxisOffset, rootView, context)?.draw(canvas)
        }
    }

    private fun setIcon(xAxisOffset: Float, rootView: View, context: Context): Drawable? {
        return when {
            (xAxisOffset < 0) ->
                getIconLeft(
                    icon = ContextCompat.getDrawable(context, R.drawable.ic_edit_24px)!!,
                    rootView = rootView
                )

            (xAxisOffset > 0) ->
                getIconRight(
                    icon = ContextCompat.getDrawable(context, R.drawable.ic_done_24px)!!,
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