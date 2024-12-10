    package com.example.cumbiacoders

    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import com.example.cumbiacoders.databinding.ActivityMainBinding

    class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false)

            if (isLoggedIn) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.btnSignUp1.setOnClickListener {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }

            binding.btnLogin1.setOnClickListener {
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
            }
        }
    }