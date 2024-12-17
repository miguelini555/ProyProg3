package com.example.cumbiacoders.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cumbiacoders.databinding.ItemHabitBinding
import com.example.cumbiacoders.dataClases.Habit

class HabitAdapter(
    private val listaHabitos: MutableList<Habit>,
    private val onDelete: (Habit) -> Unit // Callback para borrar
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    // ViewHolder
    class HabitViewHolder(val binding: ItemHabitBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit, onDelete: (Habit) -> Unit) {
            binding.textViewNombre.text = habit.nombre
            binding.textViewHora.text = habit.hora
            binding.checkBoxCompletado.isChecked = habit.completado

            // Checkbox para marcar completado
            binding.checkBoxCompletado.setOnCheckedChangeListener { _, isChecked ->
                habit.completado = isChecked
            }

            // Botón Borrar
            binding.btnDelete.setOnClickListener {
                onDelete(habit) // Llama al callback para borrar
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(listaHabitos[position], onDelete)
    }

    override fun getItemCount(): Int = listaHabitos.size

    // Función para actualizar la lista
    fun actualizarLista(nuevaLista: List<Habit>) {
        listaHabitos.clear()
        listaHabitos.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
