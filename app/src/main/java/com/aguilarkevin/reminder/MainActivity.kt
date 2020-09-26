package com.aguilarkevin.reminder

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aguilarkevin.reminder.todayEventsFragment.TodayFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var fab:FloatingActionButton
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager:ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        tabLayout = findViewById(R.id.tablayout)
        viewPager = findViewById(R.id.viewpager_tabs)

        tabLayout.addTab(
            tabLayout.newTab().setText(R.string.today).setIcon(R.drawable.ic_today_24px)
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(R.string.All).setIcon(R.drawable.ic_date_range_24px)
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(R.string.settings).setIcon(R.drawable.ic_settings_24px)
        )
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        viewPager.adapter = Adapter(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }
}

class Adapter (private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int): FragmentPagerAdapter(fm){
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                TodayFragment()
            }
            1 -> {
                AllEventsFragment()
            }
            2 -> {
                // val movieFragment = MovieFragment()
                SettingsFragment()
            }
            else -> TODO()
        }
    }

}