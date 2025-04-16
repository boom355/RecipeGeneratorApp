package com.example.myapplication.network

import com.example.myapplication.response.ChatRequest
import com.example.myapplication.response.ChatResponse
import com.example.myapplication.utils.OPENAI_API_KEY
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @Headers("Authorization: Bearer $OPENAI_API_KEY", "Content-Type: application/json")
    @POST("v1/completions")
    fun createChatCompletion(@Body request: ChatRequest): Call<ChatResponse>
}
