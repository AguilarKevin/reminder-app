package com.aguilarkevin.reminder.app.activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aguilarkevin.reminder.R
import com.noowenz.customdatetimepicker.CustomDateTimePicker
import kotlinx.android.synthetic.main.activity_new_event.*
import kotlinx.android.synthetic.main.content_new_event.*
import kotlinx.coroutines.Runnable
import java.util.*

class NewEventActivity : AppCompatActivity() {

    private lateinit var date: String
    private var calendarObject: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) Handler().postDelayed(Runnable {
                showDateTimePicker()
            }, 110)
            else date_input.setText("")
        }

        done_btn.setOnClickListener {
            val replyIntent = Intent()
            if (title_input.text!!.isEmpty())
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG).show()
            else {
                replyIntent.putExtra("Title", title_input.text.toString())
                replyIntent.putExtra("Description", description_input.text.toString())
                replyIntent.putExtra("Date", date_input.text.toString())
                replyIntent.putExtra("Type", "Event")
                replyIntent.putExtra("timeInMillis", calendarObject.timeInMillis)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }

    }

    private fun showDateTimePicker() {
        CustomDateTimePicker(this, object : CustomDateTimePicker.ICustomDateTimeListener {
            override fun onCancel() {
                switchNotifications.isChecked = false
            }

            override fun onSet(
                dialog: Dialog,
                calendarSelected: Calendar,
                dateSelected: Date,
                year: Int,
                monthFullName: String,
                monthShortName: String,
                monthNumber: Int,
                day: Int,
                weekDayFullName: String,
                weekDayShortName: String,
                hour24: Int,
                hour12: Int,
                min: Int,
                sec: Int,
                AM_PM: String
            ) {
                calendarObject.set(year, monthNumber, day, hour24, min, 0)
                date = "$weekDayShortName $day, $monthShortName $hour12:$min $AM_PM"
                date_input.setText(date)
            }

        }).showDialog()
    }
}