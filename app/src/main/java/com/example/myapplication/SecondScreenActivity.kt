package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySecondScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SecondScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val recipe = intent.getStringExtra("recipe")
        binding.recipeTextView.text = recipe?.let { Editable.Factory.getInstance().newEditable(it) } ?: Editable.Factory.getInstance().newEditable("No recipe")

        binding.addToFavoritesButton.setOnClickListener {
            addToFavorites(recipe ?: "")
        }
    }

    private fun addToFavorites(recipe: String) {
        val userId = auth.currentUser?.uid ?: return
        database.child("users").child(userId).child("favorites").push().setValue(recipe)
    }
}
