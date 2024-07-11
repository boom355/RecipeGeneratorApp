package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityFirstScreenBinding
import com.example.myapplication.repository.ChatRepository

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding
    private val chatRepository = ChatRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        binding.usernameTextView.text = if (!username.isNullOrBlank()) {
            "Welcome, $username"
        } else {
            "Welcome, Guest"
        }

        binding.generate.setOnClickListener {
            val ingredients = binding.enterIngredients.text.toString()
            val selectedLevel = when {
                binding.levelBeginner.isChecked -> 1
                binding.levelIntermediate.isChecked -> 2
                binding.levelAdvanced.isChecked -> 3
                else -> 0
            }
            val selectedTime = when {
                binding.timeLessThan15.isChecked -> 15
                binding.timeLessThan30.isChecked -> 30
                binding.timeLessThan45.isChecked -> 45
                binding.timeLessThan60.isChecked -> 60
                else -> 0
            }
            val message = "Generate a recipe with the following ingredients: $ingredients, for a level $selectedLevel cook, within $selectedTime minutes."
            generateRecipe(message)
        }

        binding.settings.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.account.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun generateRecipe(message: String) {
        chatRepository.createChatCompletion(message) { result ->
            runOnUiThread {
                val intent = Intent(this, SecondScreenActivity::class.java)
                intent.putExtra("recipe", result)
                startActivity(intent)
            }
        }
    }
}
