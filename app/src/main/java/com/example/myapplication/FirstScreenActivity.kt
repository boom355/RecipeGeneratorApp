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
        if (username != null && username.isNotBlank()) {
            binding.usernameTextView.text = " Welcome, $username"
        } else {
            binding.usernameTextView.text = "Welcome, Guest"
        }

        binding.generate.setOnClickListener {
            val ingredients = binding.enterIngredients.text.toString()

            var selectedLevel = 0 // Default to 0 or handle no selection case
            if (binding.levelBeginner.isChecked) {
                selectedLevel = 1
            } else if (binding.levelIntermediate.isChecked) {
                selectedLevel = 2
            } else if (binding.levelAdvanced.isChecked) {
                selectedLevel = 3
            }

            var selectedTime = 0 // Default to 0 or handle no selection case
            if (binding.timeLessThan15.isChecked) {
                selectedTime = 15
            } else if (binding.timeLessThan30.isChecked) {
                selectedTime = 30
            } else if (binding.timeLessThan45.isChecked) {
                selectedTime = 45
            } else if (binding.timeLessThan60.isChecked) {
                selectedTime = 60
            }

            println("Ingredients: $ingredients")
            println("Cooking Level: $selectedLevel")
            println("Selected Cooking Time: $selectedTime minutes")

            val intent = Intent(this, SecondScreenActivity::class.java)
            startActivity(intent)
        }

        binding.settings.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.account.setOnClickListener {
            println("Settings icon clicked")
        }
    }
}
