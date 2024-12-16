package com.example.cumbiacoders

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cumbiacoders.dataClases.Habit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddHabitActivity : AppCompatActivity() {

    // Declaración de las vistas
    private lateinit var nombreHabit: EditText
    private lateinit var horaHabit: EditText
    private lateinit var rgFrecuencia: RadioGroup
    private lateinit var btnAddHabit: Button
    private lateinit var btnReturn: Button // Botón Return agregado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habbit)

        // Inicialización de las vistas
        nombreHabit = findViewById(R.id.nombreHabbit)
        horaHabit = findViewById(R.id.etHora)
        rgFrecuencia = findViewById(R.id.rgFrecuencia)
        btnAddHabit = findViewById(R.id.btnAddHabit)
        btnReturn = findViewById(R.id.btnReturn) // Inicializa el botón Return

        // Acción para el botón "Add Habit"
        btnAddHabit.setOnClickListener {
            val nombre = nombreHabit.text.toString().trim()
            val hora = horaHabit.text.toString().trim()
            val categoria = obtenerCategoriaSeleccionada()

            if (nombre.isNotEmpty() && hora.isNotEmpty() && categoria.isNotEmpty()) {
                val habit = Habit(nombre, hora, categoria)
                guardarHabit(habit)
                Toast.makeText(this, "Hábito guardado exitosamente", Toast.LENGTH_SHORT).show()
                finish() // Cierra la actividad
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción para el botón "Return"
        btnReturn.setOnClickListener {
            finish() // Cierra la actividad y regresa a la anterior
        }
    }

    // Obtener la categoría seleccionada del RadioGroup
    private fun obtenerCategoriaSeleccionada(): String {
        val selectedId = rgFrecuencia.checkedRadioButtonId
        return if (selectedId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedId)
            selectedRadioButton.text.toString()
        } else {
            ""
        }
    }

    // Guardar el hábito en SharedPreferences
    private fun guardarHabit(habit: Habit) {
        val sharedPreferences = getSharedPreferences("habits_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val listaActual = cargarHabits()
        listaActual.add(habit)

        val json = gson.toJson(listaActual)
        editor.putString("habits", json)
        editor.apply()
    }

    // Cargar la lista actual de hábitos
    private fun cargarHabits(): MutableList<Habit> {
        val sharedPreferences = getSharedPreferences("habits_prefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("habits", null)
        val type = object : TypeToken<MutableList<Habit>>() {}.type
        return if (json != null) Gson().fromJson(json, type) else mutableListOf()
    }
}

