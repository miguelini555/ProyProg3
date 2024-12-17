package com.example.cumbiacoders

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cumbiacoders.dataClases.Habit
import com.example.cumbiacoders.databinding.ActivityHomeBinding
import com.example.taskly.adapters.HabitAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val dailyAdapter = HabitAdapter(mutableListOf())
    private val weeklyAdapter = HabitAdapter(mutableListOf())
    private val monthlyAdapter = HabitAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startActivityButtonHome.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }

        binding.profileButtonHome.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.habitHistoryButtonHome.setOnClickListener {
            val intent = Intent(this, HabitHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, AddHabitActivity::class.java)
            startActivity(intent)
        }


        setUpRecyclerViews()


        cargarHabits()
    }

    private fun setUpRecyclerViews() {

        binding.dailyTasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = dailyAdapter
        }


        binding.weeklyTasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = weeklyAdapter
        }


        binding.hourlyTasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = monthlyAdapter
        }
    }

    private fun cargarHabits() {

        val sharedPreferences = getSharedPreferences("habits_prefs", MODE_PRIVATE)
        val json = sharedPreferences.getString("habits", null)
        val type = object : TypeToken<List<Habit>>() {}.type
        val habits = if (json != null) Gson().fromJson<List<Habit>>(json, type) else emptyList()


        dailyAdapter.actualizarLista(habits.filter { it.categoria == "Daily" })
        weeklyAdapter.actualizarLista(habits.filter { it.categoria == "Weekly" })
        monthlyAdapter.actualizarLista(habits.filter { it.categoria == "Monthly" })
    }


    override fun onResume() {
        super.onResume()
        cargarHabits()
    }
}

