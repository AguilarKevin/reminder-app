package com.aguilarkevin.reminder.app.activities

import android.app.Activity
import android.content.Intent
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
            val replyIntent = Intent()
            if (title_input.text!!.isEmpty())
                Toast.makeText(this, R.string.add_a_title, Toast.LENGTH_LONG).show()
            else {
                replyIntent.putExtra("title", title_input.text.toString())
                replyIntent.putExtra("desc", description_input.text.toString())
                replyIntent.putExtra("date", date_input.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }
}