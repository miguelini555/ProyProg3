package com.example.cumbiacoders

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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
            if (binding.btnStart.text == "Restart") {
                resetTimer()
            } else {
                val timeInput = binding.timerText.text.toString()
                binding.label.text="In process"
                Glide.with(this).load(R.drawable.disc_moving).into(binding.disc)
                val totalTimeInMillis = parseTimeToMillis(timeInput)

                if (totalTimeInMillis > 0) {
                    startTimer(totalTimeInMillis)
                    isPaused = false
                    binding.btnStart.text = "Restart"
                } else {
                    Toast.makeText(this, "Please enter a valid time!", Toast.LENGTH_SHORT).show()
                }
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
                    binding.label.text="On Break"
                    Glide.with(this).load(R.drawable.disc).into(binding.disc)
                }
            } else {
                Toast.makeText(this, "No timer running to pause!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnReturnMenu.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun parseTimeToMillis(time: String): Long {
        return try {
            val parts = time.split(":")
            val hours = parts[0].toInt()
            val minutes = parts[1].toInt()
            val seconds = parts[2].toInt()
            (hours * 3600 + minutes * 60 + seconds) * 1000L
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
                binding.timerText.setText("00:00:00")
                Toast.makeText(this@TimerActivity, "Timer finished!", Toast.LENGTH_SHORT).show()
                binding.btnPause.text = "Pause"
                binding.btnStart.text = "Start"
                isPaused = false
            }
        }
        timer?.start()
    }

    private fun formatMillisToTime(millis: Long): String {
        val hours = millis / 3600000
        val minutes = (millis % 3600000) / 60000
        val seconds = (millis % 60000) / 1000
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun resetTimer() {
        timer?.cancel()
        timer = null
        timeRemaining = 0
        isPaused = false

        binding.timerText.setText("00:00:00")
        binding.btnPause.text = "Pause"
        binding.btnStart.text = "Start"
        binding.label.text="Start An Activity"
        Glide.with(this).load(R.drawable.disc).into(binding.disc)
    }
}
