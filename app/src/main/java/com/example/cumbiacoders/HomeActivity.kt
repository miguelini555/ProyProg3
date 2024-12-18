package com.example.cumbiacoders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cumbiacoders.adapters.HabitAdapter
import com.example.cumbiacoders.dataClases.Habit
import com.example.cumbiacoders.databinding.ActivityHomeBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    // Adaptadores para cada RecyclerView
    private lateinit var dailyAdapter: HabitAdapter
    private lateinit var weeklyAdapter: HabitAdapter
    private lateinit var monthlyAdapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar botones de navegación
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
        // Adaptador Daily
        dailyAdapter = HabitAdapter(mutableListOf()) { habit ->
            borrarHabit(habit)
        }
        binding.dailyTasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = dailyAdapter
        }

        // Adaptador Weekly
        weeklyAdapter = HabitAdapter(mutableListOf()) { habit ->
            borrarHabit(habit)
        }
        binding.weeklyTasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = weeklyAdapter
        }

        // Adaptador Monthly
        monthlyAdapter = HabitAdapter(mutableListOf()) { habit ->
            borrarHabit(habit)
        }
        binding.hourlyTasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = monthlyAdapter
        }
    }

    private fun cargarHabits() {
        // Obtener hábitos desde SharedPreferences
        val sharedPreferences = getSharedPreferences("habits_prefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("habits", null)
        val type = object : TypeToken<List<Habit>>() {}.type
        val habits = if (json != null) Gson().fromJson<List<Habit>>(json, type) else emptyList()

        // Filtrar y actualizar adaptadores según la frequencia
        dailyAdapter.actualizarLista(habits.filter { it.categoria == "Daily" })
        weeklyAdapter.actualizarLista(habits.filter { it.categoria == "Weekly" })
        monthlyAdapter.actualizarLista(habits.filter { it.categoria == "Monthly" })
    }

    private fun borrarHabit(habit: Habit) {
        // Obtener lista actual desde SharedPreferences
        val sharedPreferences = getSharedPreferences("habits_prefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("habits", null)
        val type = object : TypeToken<MutableList<Habit>>() {}.type
        val habits = if (json != null) Gson().fromJson<MutableList<Habit>>(json, type) else mutableListOf()

        // Eliminar el hábito específico
        habits.removeIf { it.nombre == habit.nombre }

        // Guardar la lista actualizada en SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("habits", Gson().toJson(habits))
        editor.apply()

        // Volver a cargar las listas
        cargarHabits()
    }

    override fun onResume() {
        super.onResume()
        cargarHabits()
    }
}
