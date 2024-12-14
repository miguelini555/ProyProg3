package com.example.cumbiacoders

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cumbiacoders.adapters.TaskAdapter
import com.example.cumbiacoders.databinding.ActivityHomeBinding
import com.example.cumbiacoders.model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var todayAdapter: TaskAdapter
    private lateinit var tomorrowAdapter: TaskAdapter
    private lateinit var dayAfterTomorrowAdapter: TaskAdapter

    private lateinit var tasksByDate: MutableMap<String, MutableList<Task>>
    private lateinit var gson: Gson
    private val sharedPreferences by lazy { getSharedPreferences("TASKS_PREFS", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gson = Gson()
        tasksByDate = loadTasks()

        // Configure adapters
        todayAdapter = TaskAdapter(tasksByDate["Hoy"] ?: mutableListOf()) { saveTasks() }
        tomorrowAdapter = TaskAdapter(tasksByDate["Mañana"] ?: mutableListOf()) { saveTasks() }
        dayAfterTomorrowAdapter = TaskAdapter(tasksByDate["Pasado Mañana"] ?: mutableListOf()) { saveTasks() }

        binding.todayTasksRecyclerView.adapter = todayAdapter
        binding.tomorrowTasksRecyclerView.adapter = tomorrowAdapter
        binding.dayAfterTomorrowTasksRecyclerView.adapter = dayAfterTomorrowAdapter

        // Button listeners
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
            val intent = Intent(this, AddHabbitActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }
    }

    // Handle results from AddTaskActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            val jsonTask = data?.getStringExtra("NEW_TASK")
            val task = gson.fromJson(jsonTask, Task::class.java)
            tasksByDate[task.date]?.add(task)
            saveTasks()
            updateRecyclerViews()
        }
    }

    // Load tasks from SharedPreferences
    private fun loadTasks(): MutableMap<String, MutableList<Task>> {
        val jsonString = sharedPreferences.getString("TASKS", null)
        return if (jsonString != null) {
            val type = object : TypeToken<MutableMap<String, MutableList<Task>>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            mutableMapOf(
                "Hoy" to mutableListOf(),
                "Mañana" to mutableListOf(),
                "Pasado Mañana" to mutableListOf()
            )
        }
    }

    // Save tasks to SharedPreferences
    private fun saveTasks() {
        val jsonString = gson.toJson(tasksByDate)
        with(sharedPreferences.edit()) {
            putString("TASKS", jsonString)
            apply()
        }
    }

    // Update RecyclerViews
    private fun updateRecyclerViews() {
        todayAdapter.notifyDataSetChanged()
        tomorrowAdapter.notifyDataSetChanged()
        dayAfterTomorrowAdapter.notifyDataSetChanged()
    }

    companion object {
        const val ADD_TASK_REQUEST = 1
    }
}
