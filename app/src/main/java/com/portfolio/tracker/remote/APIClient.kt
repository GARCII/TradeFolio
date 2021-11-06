package com.portfolio.tracker.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.portfolio.tracker.util.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    private var retrofit: Retrofit

    init {
        val factory = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
        val client = OkHttpClient.Builder().build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    fun getAccountApi(): AccountApi = retrofit.create(AccountApi::class.java)
}