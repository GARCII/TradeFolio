package com.portfolio.tracker.model

import java.util.*

data class CurrencyInfoResponse(
    val urls: List<String>,
    val logo: String,
    val name: String,
    val symbol: String,
    val slug: String,
    val description: String,
    val date_added: Date,
    val status: CurrencyStatusResponse
)