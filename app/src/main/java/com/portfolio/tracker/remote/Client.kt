package com.portfolio.tracker.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.portfolio.tracker.util.BYBIT_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

    private var retrofit: Retrofit

    init {
        val factory = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
        val client = OkHttpClient.Builder().build()
        retrofit = Retrofit.Builder()
            .baseUrl(BYBIT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }
}