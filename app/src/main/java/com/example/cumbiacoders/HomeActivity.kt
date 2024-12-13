package com.example.cumbiacoders

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cumbiacoders.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startActivityButtonHome.setOnClickListener {
            val intent = Intent(this,TimerActivity::class.java)
            startActivity(intent)

        }
        binding.profileButtonHome.setOnClickListener {

        }
        binding.achievementButtonHome.setOnClickListener {
            val intent = Intent(this, AchievementesActivity ::class.java)
            startActivity(intent)

        }
        binding.habitHistoryButtonHome.setOnClickListener {

        }
        binding.addTaskButton.setOnClickListener {

        }
        }
    }
