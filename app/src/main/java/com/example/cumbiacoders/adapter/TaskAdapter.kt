package com.example.cumbiacoders.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cumbiacoders.R
import com.example.cumbiacoders.model.Task

class TaskAdapter(
    private val tasks: MutableList<Task>, // Lista de tareas que se mostrarán
    private val onTaskCheckedChange: (Task) -> Unit // Callback para manejar cambios en el CheckBox
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder: Representa cada ítem de la lista
    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: TextView = view.findViewById(R.id.taskTitle)
        val taskCheckBox: CheckBox = view.findViewById(R.id.taskCheckBox)
    }

    // Inflar el diseño de cada ítem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    // Vincular datos con las vistas
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskTitle.text = task.title
        holder.taskCheckBox.isChecked = task.isCompleted

        // Manejar cambios en el CheckBox
        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            onTaskCheckedChange(task) // Notificar cambios al RecyclerView
        }
    }

    // Número total de ítems
    override fun getItemCount(): Int = tasks.size
}
