package com.portfolio.tracker.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.portfolio.tracker.util.COIN_MARKET_CAP_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

    private var retrofit: Retrofit

    init {
        val factory = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
        val client = OkHttpClient.Builder().build()
        retrofit = Retrofit.Builder()
            .baseUrl(COIN_MARKET_CAP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    fun getCoinMarketApi(): CoinMarketApi = retrofit.create(CoinMarketApi::class.java)
}