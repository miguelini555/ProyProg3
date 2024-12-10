package com.example.cumbiacoders

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cumbiacoders.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerBinding
    private var timer: CountDownTimer? = null
    private var isPaused = false
    private var timeRemaining: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStart.setOnClickListener {
            val timeInput = binding.timerText.text.toString()
            val totalTimeInMillis = parseTimeToMillis(timeInput)

            if (totalTimeInMillis > 0) {
                startTimer(totalTimeInMillis)
                isPaused = false
            } else {
                Toast.makeText(this, "Please enter a valid time!", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnPause.setOnClickListener {
            if (timer != null) {
                if (isPaused) {

                    startTimer(timeRemaining)
                    isPaused = false
                    binding.btnPause.text = "Pause"
                } else {

                    timer?.cancel()
                    isPaused = true
                    binding.btnPause.text = "Resume"
                }
            } else {
                Toast.makeText(this, "No timer running to pause!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parseTimeToMillis(time: String): Long {
        return try {
            val parts = time.split(":")
            val minutes = parts[0].toInt()
            val seconds = parts[1].toInt()
            (minutes * 60 + seconds) * 1000L
        } catch (e: Exception) {
            0L
        }
    }

    private fun startTimer(milliseconds: Long) {
        timer?.cancel()

        timer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                binding.timerText.setText(formatMillisToTime(millisUntilFinished))
            }

            override fun onFinish() {
                binding.timerText.setText("00:00")
                Toast.makeText(this@TimerActivity, "Timer finished!", Toast.LENGTH_SHORT).show()
                binding.btnPause.text = "Pause"
                isPaused = false
            }
        }
        timer?.start()
    }

    private fun formatMillisToTime(millis: Long): String {
        val minutes = millis / 60000
        val seconds = (millis % 60000) / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }
}
