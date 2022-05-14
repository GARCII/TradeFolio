package com.portfolio.tracker.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class CoinMarketResponse(
    val data: Map<String, CoinMarketDataResponse>,
    val status :CurrencyStatusResponse
)

data class CoinMarketDataResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("slug")
    val slug: String,

    @SerializedName("cmc_rank")
    val rank: Int,

    @SerializedName("circulating_supply")
    val circulationSupply: Double,

    @SerializedName("total_supply")
    val totalSupply: Double,

    @SerializedName("max_supply")
    val maxSupply: Double,

    @SerializedName("quote")
    val priceQuotes: Map<String, PriceQuoteResponse>
)

data class PriceQuoteResponse(
    @SerializedName("price")
    val price: Double,
    @SerializedName("volume_24h")
    val dayVolume: Double,
    @SerializedName("market_cap")
    val marketCap: Double,
    @SerializedName("percent_change_1h")
    val hourChange: Double,
    @SerializedName("volume_change_24h")
    val dayChange: Double,
    @SerializedName("percent_change_7d")
    val weekChange: Double
)

data class CurrencyStatusResponse(
    val timestamp: Date,
    @SerializedName("error_code")
    val errorCode: Int,
    @SerializedName("error_message")
    val errorMessage: String,
    val elapsed: Int,
    @SerializedName("credit_count")
    val creditCount: Int
)

data class CoinMarket(
    val id: String,
    val name: String,
    val symbol: String,
    val slug: String,
    val rank: Int,
    val circulationSupply: String,
    val totalSupply: String,
    val maxSupply: String,
    val priceQuotes: PriceQuote
)

data class PriceQuote(
    val price: String,
    val dayVolume: String,
    val marketCap: String,
    val hourChange: String,
    val dayChange: String,
    val weekChange: String
)

