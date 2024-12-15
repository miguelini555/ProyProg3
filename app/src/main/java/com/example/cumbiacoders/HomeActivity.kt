package com.example.cumbiacoders

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cumbiacoders.adapters.TaskAdapter
import com.example.cumbiacoders.databinding.ActivityHomeBinding
import com.example.cumbiacoders.model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var hourlyAdapter: TaskAdapter
    private lateinit var dailyAdapter: TaskAdapter
    private lateinit var weeklyAdapter: TaskAdapter

    private lateinit var tasksByFrequency: MutableMap<String, MutableList<Task>>
    private lateinit var gson: Gson
    private val sharedPreferences by lazy { getSharedPreferences("TASKS_PREFS", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gson = Gson()
        tasksByFrequency = loadTasks()

        // Configure adapters
        hourlyAdapter = TaskAdapter(tasksByFrequency["Hourly"] ?: mutableListOf()) { saveTasks() }
        dailyAdapter = TaskAdapter(tasksByFrequency["Daily"] ?: mutableListOf()) { saveTasks() }
        weeklyAdapter = TaskAdapter(tasksByFrequency["Weekly"] ?: mutableListOf()) { saveTasks() }

        binding.hourlyTasksRecyclerView.adapter = hourlyAdapter
        binding.dailyTasksRecyclerView.adapter = dailyAdapter
        binding.weeklyTasksRecyclerView.adapter = weeklyAdapter

        // Botón para agregar una nueva tarea
        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, AddHabbitActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }
    }

    // Manejar el resultado de AddHabitActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            val jsonTask = data?.getStringExtra("NEW_TASK")
            val task = gson.fromJson(jsonTask, Task::class.java)

            // Usa el campo `frequency` para determinar a qué lista agregar la tarea
            when (task.frequency) {
                "Hourly" -> tasksByFrequency["Hourly"]?.add(task)
                "Daily" -> tasksByFrequency["Daily"]?.add(task)
                "Weekly" -> tasksByFrequency["Weekly"]?.add(task)
            }

            saveTasks() // Guarda las tareas en SharedPreferences
            updateRecyclerViews() // Actualiza los RecyclerView
        }
    }

    // Cargar tareas desde SharedPreferences
    private fun loadTasks(): MutableMap<String, MutableList<Task>> {
        val jsonString = sharedPreferences.getString("TASKS", null)
        return if (jsonString != null) {
            val type = object : TypeToken<MutableMap<String, MutableList<Task>>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            mutableMapOf(
                "Hourly" to mutableListOf(),
                "Daily" to mutableListOf(),
                "Weekly" to mutableListOf()
            )
        }
    }

    // Guardar tareas en SharedPreferences
    private fun saveTasks() {
        val jsonString = gson.toJson(tasksByFrequency)
        with(sharedPreferences.edit()) {
            putString("TASKS", jsonString)
            apply()
        }
    }

    // Actualizar los RecyclerViews
    private fun updateRecyclerViews() {
        hourlyAdapter.notifyDataSetChanged()
        dailyAdapter.notifyDataSetChanged()
        weeklyAdapter.notifyDataSetChanged()
    }

    companion object {
        const val ADD_TASK_REQUEST = 1
    }
}