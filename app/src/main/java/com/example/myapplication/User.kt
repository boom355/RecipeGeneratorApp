package com.example.myapplication

data class User(
    val username: String? = "",
    val email: String? = "",
    val password: String? = "",
    val favorites: List<String> = listOf()
)
