package com.example.cumbiacoders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cumbiacoders.adapters.HabitAdapter
import com.example.cumbiacoders.dataClases.Habit
import com.example.cumbiacoders.databinding.ActivityHabitHistoryBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHabitHistoryBinding
    private lateinit var historyAdapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHabitHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReturnMenuHh.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        setUpRecyclerViews()
        loadHistory()
    }

    private fun setUpRecyclerViews() {
        historyAdapter = HabitAdapter(mutableListOf()) { habit ->

        }
        binding.habitHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HabitHistoryActivity)
            adapter = historyAdapter
        }
    }

    private fun loadHistory() {
        val sharedPreferences = getSharedPreferences("habits_prefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("habits", null)

        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<Habit>>() {}.type
            val habits = Gson().fromJson<List<Habit>>(json, type)

            historyAdapter.actualizarLista(habits)
        } else {
            historyAdapter.actualizarLista(emptyList())
        }
    }
}
