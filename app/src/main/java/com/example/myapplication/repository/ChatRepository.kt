package com.example.myapplication.repository

import com.example.myapplication.network.ApiClient
import com.example.myapplication.response.ChatRequest
import com.example.myapplication.response.ChatResponse
import com.example.myapplication.response.Message
import com.example.myapplication.utils.CHAT_GPT_MODEL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRepository {

    private val apiClient = ApiClient.getInstance()

    fun createChatCompletion(message: String, callback: (String) -> Unit) {
        val messages = listOf(Message(role = "user", content = message))
        val request = ChatRequest(messages = messages, model = CHAT_GPT_MODEL)
        val call = apiClient.createChatCompletion(request)

        call.enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.choices?.firstOrNull()?.message?.content ?: "No response"
                    callback(result)
                } else {
                    callback("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                callback("Failure: ${t.message}")
            }
        })
    }
}
