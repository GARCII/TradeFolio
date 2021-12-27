package com.portfolio.tracker.remote

import com.portfolio.tracker.model.CoinMarketResponse
import com.portfolio.tracker.util.COIN_MARKET_CAP_API_KEY
import com.portfolio.tracker.util.CURRENCY_QUOTE_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface CoinMarketApi {
    @GET(CURRENCY_QUOTE_URL)
    suspend fun getCurrencies(
        @Header("X-CMC_PRO_API_KEY") apiKey: String = COIN_MARKET_CAP_API_KEY,
        @Query("symbol") symbol: String,
    ): Response<CoinMarketResponse>
}