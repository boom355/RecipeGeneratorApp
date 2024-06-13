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

        // Setting up actions for the button
        binding.generate.setOnClickListener {
            // Retrieve values from EditTexts
            val username = binding.username.text.toString()
            val ingredients = binding.enterIngredients.text.toString()
            val time = binding.time.text.toString()
            val level = binding.level.text.toString()

            // For now, let's just print the values to the log
            println("Username: $username")
            println("Ingredients: $ingredients")
            println("Time: $time")
            println("Level: $level")

            // Start the second activity
            val intent = Intent(this, SecondScreenActivity::class.java)
            startActivity(intent)
        }

        // Optionally, set up listeners for the ImageViews
        binding.account.setOnClickListener {
            // Handle account icon click
            val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
        }

        binding.settings.setOnClickListener {
            // Handle settings icon click
            println("Settings icon clicked")
        }
    }
}
