package com.example.cumbiacoders

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cumbiacoders.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)


        val usernameEditText: EditText = binding.usernameEditText
        val emailTextView: TextView = binding.emailText
        val passwordEditText: EditText = binding.passwordEditText
        val saveChangesButton: Button = binding.saveChangesButton


        val username = sharedPreferences.getString("USER_NAME", "Unknown Username")
        val email = sharedPreferences.getString("USER_EMAIL", "Unknown Email")


        usernameEditText.setText(username)
        emailTextView.text = email
        passwordEditText.setText("••••••••")

        saveChangesButton.setOnClickListener {
            val newUsername = usernameEditText.text.toString()

            if (newUsername.isBlank()) {
                Toast.makeText(this, "El nombre de usuario no puede estar vacío", Toast.LENGTH_SHORT).show()
            } else {

                with(sharedPreferences.edit()) {
                    putString("USER_NAME", newUsername)
                    apply()
                }

                Toast.makeText(this, "Cambios guardados exitosamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
