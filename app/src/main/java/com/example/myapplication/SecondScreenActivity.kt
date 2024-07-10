package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySecondScreenBinding

class SecondScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the recipe from the intent
        val recipe = intent.getStringExtra("recipe")
        if (recipe != null) {
            displayRecipe(recipe)
        } else {
            Toast.makeText(this, "No recipe provided", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayRecipe(recipe: String) {
        binding.enterIngredients.setText(recipe)
    }
}
