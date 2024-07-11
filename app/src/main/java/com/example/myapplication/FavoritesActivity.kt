package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityFavoritesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var favoritesAdapter: FavoritesAdapter
    private val favoriteRecipes = mutableListOf<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(this)
        favoritesAdapter = FavoritesAdapter(favoriteRecipes)
        binding.favoritesRecyclerView.adapter = favoritesAdapter

        loadFavoriteRecipes()
    }

    private fun loadFavoriteRecipes() {
        val userId = auth.currentUser?.uid ?: return
        val userFavoritesRef = database.child("users").child(userId).child("favorites")

        userFavoritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                favoriteRecipes.clear()
                for (recipeSnapshot in snapshot.children) {
                    val recipe = recipeSnapshot.getValue(Recipe::class.java)
                    if (recipe != null) {
                        favoriteRecipes.add(recipe)
                    }
                }
                favoritesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })
    }
}
