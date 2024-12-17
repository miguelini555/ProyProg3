package com.example.cumbiacoders.dataClases

import java.io.Serializable
import java.net.IDN

data class Habit(
    var nombre: String,
    val hora: String,
    val categoria: String, //daily, weekly, monthly
    var completado: Boolean = false
) : Serializable

