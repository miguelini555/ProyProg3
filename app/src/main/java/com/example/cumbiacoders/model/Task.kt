package com.example.cumbiacoders.model

import java.io.Serializable

data class Task(
    val id: Int,
    var title: String,
    var isCompleted: Boolean,
    var date: String
) : Serializable