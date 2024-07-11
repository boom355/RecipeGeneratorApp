package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.utils.Key

class EnterApiKeyActivity : AppCompatActivity() {

    private lateinit var apiKeyEditText: EditText
    private lateinit var verifyApiKeyButton: Button

    private val expectedApiKey = Key.OPENAI_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_key)

        apiKeyEditText = findViewById(R.id.apiKeyEditText)
        verifyApiKeyButton = findViewById(R.id.verifyApiKeyButton)

        verifyApiKeyButton.setOnClickListener {
            val apiKey = apiKeyEditText.text.toString().trim()

            if (apiKey == expectedApiKey) {
                navigateToLoginScreen()
            } else {
                Toast.makeText(this, "Invalid API Key", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToLoginScreen() {
        // Navigate to the login screen
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finish this activity to prevent returning to it with the back button
    }
}
