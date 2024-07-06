package com.example.myapplication

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.registerButton.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Create user with Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                                if (verificationTask.isSuccessful) {
                                    val userId = user.uid
                                    val userData = User(username, email, password)

                                    // Save user data to Firebase Realtime Database
                                    database.reference.child("users").child(userId).setValue(userData)
                                        .addOnCompleteListener { saveTask ->
                                            if (saveTask.isSuccessful) {
                                                Toast.makeText(this, "Registration Successful. Please check your email for verification.", Toast.LENGTH_LONG).show()

                                                // Pass the username, email, and password back to FirstScreenActivity
                                                val intent = Intent(this, FirstScreenActivity::class.java).apply {
                                                    putExtra("username", username)
                                                }
                                                startActivity(intent)
                                                finish()
                                            } else {
                                                Log.e("RegisterActivity", "Failed to save user data to Firebase", saveTask.exception)
                                                Toast.makeText(this, "Failed to save user data to Firebase", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                } else {
                                    Log.e("RegisterActivity", "Failed to send verification email.", verificationTask.exception)
                                    Toast.makeText(this, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Log.e("RegisterActivity", "Registration Failed: ${task.exception?.message}", task.exception)
                            Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
