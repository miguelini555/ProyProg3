package com.example.cumbiacoders

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TimerActivity : AppCompatActivity() {

    private lateinit var timerText: TextView
    private lateinit var pauseButton: Button
    private lateinit var startButton: Button

    private var timerDuration = 3 * 60 * 1000L
    private var timeRemaining = timerDuration
    private var timer: CountDownTimer? = null
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        timerText = findViewById(R.id.timer_text)
        pauseButton = findViewById(R.id.btn_pause)
        startButton = findViewById(R.id.btn_start)

        startButton.setOnClickListener {
            startTimer()
        }

        pauseButton.setOnClickListener {
            pauseTimer()
        }
    }

    private fun startTimer() {
        timer?.cancel() // Cancelar cualquier temporizador previo si existe
        timer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timerText.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerText.text = "00:00"
            }
        }.start()
        isPaused = false
    }

    private fun pauseTimer() {
        if (!isPaused) {
            timer?.cancel() // Pausar el temporizador actual
            isPaused = true
        }
    }
}

