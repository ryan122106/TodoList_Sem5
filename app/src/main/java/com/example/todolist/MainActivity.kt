package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.example.tested.NewTodoViewPagerAdapter
import com.example.todolist.databases.AppDatabase
import com.example.todolist.daos.TodoDao
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var database: AppDatabase
        lateinit var todoDao: TodoDao
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ✅ SINGLE DATABASE INSTANCE
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my-database"
        )
            .fallbackToDestructiveMigration()
            .build()

        todoDao = database.todoDao()

        // ViewPager
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = NewTodoViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "New Word" else "Completed"
        }.attach()
    }
}