package com.example.cumbiacoders

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cumbiacoders.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin2.setOnClickListener {
            val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putBoolean("IS_LOGGED_IN", true)
                apply()
            }
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        fun loginUsuario(){
            val password: String = binding.enterPasswordLogIn.text.toString()
            val email: String = binding.enterEmailLogIn.text.toString()
            auth.signInWithEmailAndPassword("email", "password")
                .addOnCompleteListener(this){ respuesta ->
                    if(respuesta.isSuccessful){


                    }
                    else {

                    }
                    }
                }
        }
    }