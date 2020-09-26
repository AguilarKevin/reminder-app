package com.aguilarkevin.reminder.app.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aguilarkevin.reminder.R
import kotlinx.android.synthetic.main.activity_new_event.*
import kotlinx.android.synthetic.main.content_new_event.*

class NewEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            date_input.isEnabled = isChecked
        }

        done_btn.setOnClickListener {
            if (title_input.text!!.isNotEmpty())
                Toast.makeText(this, R.string.add_a_title, Toast.LENGTH_LONG).show()
            else {
                /*val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "database-name")
                    .build()

                db.eventDao().insert(Event())*/
            }

        }

    }
}