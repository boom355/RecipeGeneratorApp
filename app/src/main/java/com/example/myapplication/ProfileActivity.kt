package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        binding.save.setOnClickListener {
            val name = binding.nameInput.text.toString().trim()
            val password = binding.PasswordInput.text.toString().trim()

            val user = auth.currentUser
            if (user != null) {
                // Update Username
                if (name.isNotEmpty()) {
                    updateUserName(name)
                }
                // Update Password
                if (password.isNotEmpty()) {
                    updateUserPassword(password)
                }
            }
        }

        binding.DeleteAccoount.setOnClickListener {
            deleteAccount()
        }
    }

    private fun updateUserName(name: String) {
        val user = auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
            if (profileTask.isSuccessful) {
                // Update user's name in Realtime Database
                val uid = user.uid
                val userRef = database.child("users").child(uid)
                userRef.child("username").setValue(name)
                    .addOnCompleteListener { dbTask ->
                        if (dbTask.isSuccessful) {
                            Toast.makeText(this, "Name updated successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to update name in database: ${dbTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Failed to update name: ${profileTask.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateUserPassword(password: String) {
        val user = auth.currentUser

        user?.updatePassword(password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to update password: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteAccount() {
        val user = auth.currentUser
        val uid = user?.uid

        // Delete user from Firebase Authentication
        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Delete user data from Realtime Database
                database.child("users").child(uid!!).removeValue().addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful) {
                        Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to delete account data: ${dbTask.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Failed to delete account: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}