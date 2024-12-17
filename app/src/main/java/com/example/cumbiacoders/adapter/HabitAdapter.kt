package com.example.taskly.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cumbiacoders.dataClases.Habit
import com.example.cumbiacoders.databinding.ItemHabitBinding

class HabitAdapter(private val listaHabitos: MutableList<Habit>) :
    RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(val binding: ItemHabitBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            binding.textViewNombre.text = habit.nombre
            binding.textViewHora.text = habit.hora
            binding.checkBoxCompletado.isChecked = habit.completado

            // Actualizar el estado del CheckBox al interactuar
            binding.checkBoxCompletado.setOnCheckedChangeListener { _, isChecked ->
                habit.completado = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(listaHabitos[position])
    }

    override fun getItemCount(): Int = listaHabitos.size

    fun actualizarLista(nuevaLista: List<Habit>) {
        listaHabitos.clear()
        listaHabitos.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
