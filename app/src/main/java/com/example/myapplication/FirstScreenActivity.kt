package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityFirstScreenBinding

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the username from the intent
        val username = intent.getStringExtra("username")

        // Set the username in the welcome TextView
        if (username != null) {
            binding.usernameTextView.text = "Welcome, $username"
        } else {
            binding.usernameTextView.text = "Welcome, Guest"
        }

        // Setting up actions for the button
        binding.generate.setOnClickListener {
            // Retrieve values from EditTexts
            val ingredients = binding.enterIngredients.text.toString()
            val time = binding.time.text.toString()
            val level = binding.level.text.toString()

            // For now, let's just print the values to the log
            println("Ingredients: $ingredients")
            println("Time: $time")
            println("Level: $level")

            // Start the second activity
            val intent = Intent(this, SecondScreenActivity::class.java)
            startActivity(intent)
        }

        // Optionally, set up listeners for the ImageViews
        binding.settings.setOnClickListener {
            // Handle account icon click
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.account.setOnClickListener {
            // Handle settings icon click
            println("Settings icon clicked")
        }
    }
}
