package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // Firebase Authentication instance
    private lateinit var auth: FirebaseAuth

    // View binding instance
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()  // Initialize Firebase Auth

        // Set click listener for login button
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            // Sign in with Firebase Authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null && user.isEmailVerified) {
                            // Navigate to FirstScreenActivity upon successful login
                            val intent = Intent(this, FirstScreenActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Please verify your email address.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Set click listener for register button
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // Handle "Forgot Password?" click event
    fun forgotPassword() {
        val email = binding.email.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email first", Toast.LENGTH_SHORT).show()
            return
        }

        // Send password reset email
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to send reset email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
//binding.registerButton.setOnClickListener: Sets a click listener on the register button (registerButton). When clicked, it navigates to the RegisterActivity.