package com.aguilarkevin.reminder.todayEventsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aguilarkevin.reminder.R
import com.aguilarkevin.reminder.todayEventsFragment.adapters.EmptinessModuleImpl
import com.aguilarkevin.reminder.todayEventsFragment.adapters.EventModule
import com.aguilarkevin.reminder.todayEventsFragment.models.EventItem
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.synthetic.main.fragment_today.*

class TodayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview_today.layoutManager = LinearLayoutManager(context)
        val oneAdapter = OneAdapter(recyclerview_today) {
            this.itemModules += EventModule()
            this.emptinessModule = EmptinessModuleImpl()
        }

        val items = ArrayList<Diffable>()
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        items.add(EventItem())
        oneAdapter.setItems(items)
    }
}