package com.example.myapplication.response

data class ChatRequest(
    val messages: List<Message>,
    val model: String
)
