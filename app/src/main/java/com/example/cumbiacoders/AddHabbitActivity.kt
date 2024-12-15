package com.example.cumbiacoders

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.cumbiacoders.databinding.ActivityAddHabbitBinding
import com.example.cumbiacoders.model.Task
import com.google.gson.Gson

class AddHabbitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddHabbitBinding
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHabbitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón para regresar al menú
        binding.btnReturn.setOnClickListener {
            finish() // Cierra la actividad sin devolver datos
        }

        // Botón para agregar un nuevo hábito
        binding.btnAddHabit.setOnClickListener {
            val habitName = binding.nombreHabbit.text.toString()
            val habitTime = binding.etHora.text.toString() // Si usas este campo
            val habitLabel = binding.etEtiqueta.text.toString()

            // Obtener la frecuencia seleccionada
            val selectedFrequencyId = binding.rgFrecuencia.checkedRadioButtonId
            val habitFrequency = when (selectedFrequencyId) {
                R.id.rbWeekly -> "Weekly"
                R.id.rbDaily -> "Daily"
                R.id.rbHourly -> "Hourly"
                else -> null
            }

            // Validar entradas
            if (habitName.isBlank() || habitFrequency == null) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear un objeto Task con los datos ingresados
            val newTask = Task(
                id = System.currentTimeMillis().toInt(),
                title = habitName,
                isCompleted = false,
                frequency = habitFrequency // Guardar la frecuencia directamente
            )

            // Devolver el objeto Task a HomeActivity como JSON
            val intent = Intent()
            intent.putExtra("NEW_TASK", gson.toJson(newTask))
            setResult(RESULT_OK, intent)
            finish() // Finaliza la actividad después de agregar el hábito
        }
    }
}