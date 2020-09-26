package com.aguilarkevin.reminder.app.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.app.adapters.EmptinessModuleImpl
import com.aguilarkevin.reminder.app.adapters.EventModule
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fab: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()*/

        recycler_view.layoutManager = LinearLayoutManager(this)
        val oneAdapter = OneAdapter(recycler_view) {
            this.itemModules += EventModule()
            this.emptinessModule = EmptinessModuleImpl()
        }

        val items = ArrayList<Diffable>()
        /*val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
        "database-name"
        ).build()

        val list = db.eventDao().getAllEvents()
        for(i in list){
            Arr
        }
*/
        oneAdapter.setItems(items)

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, NewEventActivity::class.java))
        }
    }
}