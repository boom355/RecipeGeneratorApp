package com.example.myapplication.network

import com.example.myapplication.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    @Volatile
    private var INSTANCE: ApiInterface? = null

    fun getInstance(): ApiInterface {
        return INSTANCE ?: synchronized(this) {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
                .also { INSTANCE = it }
        }
    }
}
