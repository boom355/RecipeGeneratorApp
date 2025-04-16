package com.example.myapplication.repository

import com.example.myapplication.network.ApiClient
import com.example.myapplication.response.ChatRequest
import com.example.myapplication.response.ChatResponse
import com.example.myapplication.response.Message
import com.example.myapplication.utils.CHAT_GPT_MODEL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import kotlinx.coroutines.*

class ChatRepository {

    private val apiService = ApiClient.apiService

    fun createChatCompletion(message: String, callback: (String) -> Unit) {
        val messages = listOf(Message(role = "user", content = message))
        val request = ChatRequest(messages = messages, model = CHAT_GPT_MODEL)
        val call = apiService.createChatCompletion(request)

        makeRequestWithRetry(call, 3, callback)
    }

    private fun makeRequestWithRetry(call: Call<ChatResponse>, retries: Int, callback: (String) -> Unit) {
        call.clone().enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.choices?.firstOrNull()?.message?.content ?: "No response"
                    callback(result)
                } else {
                    if (response.code() == 429 && retries > 0) {
                        val retryAfter = response.headers()["Retry-After"]?.toLongOrNull() ?: (1L shl (3 - retries))
                        Log.w("ChatRepository", "Rate limit hit. Retrying after $retryAfter seconds.")
                        GlobalScope.launch {
                            delay(retryAfter * 1000)
                            makeRequestWithRetry(call, retries - 1, callback)
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("ChatRepository", "Error response: $errorBody")
                        callback("Error: ${response.code()} - ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                Log.e("ChatRepository", "Network failure: ${t.message}", t)
                if (retries > 0) {
                    GlobalScope.launch {
                        delay((1L shl (3 - retries)) * 1000)
                        makeRequestWithRetry(call, retries - 1, callback)
                    }
                } else {
                    callback("Failure: ${t.message}")
                }
            }
        })
    }
}
