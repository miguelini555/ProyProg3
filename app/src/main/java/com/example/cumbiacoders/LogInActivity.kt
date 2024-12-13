package com.example.cumbiacoders

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cumbiacoders.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin2.setOnClickListener {
            val email = binding.enterEmailLogIn.text.toString().trim()
            val password = binding.enterPasswordLogIn.text.toString().trim()

            // Validaciones
            when {
                email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT)
                        .show()
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "Por favor, ingresa un correo válido", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    binding.btnLogin2.isEnabled = false
                    loginUsuario(email, password)
                }
            }
        }
    }

    private fun loginUsuario(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.btnLogin2.isEnabled = true // Rehabilitar botón

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putBoolean("IS_LOGGED_IN", true)
                            putString("USER_EMAIL", user.email)
                            putString(
                                "USER_NAME",
                                user.email?.split("@")?.get(0) ?: "Unknown"
                            )
                            apply()
                        }

                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Credenciales inválidas, verifica tu contraseña"
                        is FirebaseAuthInvalidUserException -> "El usuario no existe o fue deshabilitado"
                        else -> task.exception?.message ?: "Error al iniciar sesión"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }
}
