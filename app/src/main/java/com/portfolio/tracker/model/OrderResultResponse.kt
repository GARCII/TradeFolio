package com.portfolio.tracker.model

data class OrderResultResponse(
    val symbol: String,
    val orderId: Long,
    val clientOrderId: String,
    val price: String,
    val origQty: String,
    val executedQty: String,
    val status: String,
    val type: String,
    val side: String,
    val isWorking: Boolean,
    val time: Long,
    val updateTime: Long
)

data class OrderResultPost(
    val symbol: String,
    val orderId: Long,
    val clientOrderId: String,
    val transactTime: Long,
    val price: String,
    val origQty: String,
    val executedQty: String,
    val status: String,
    val timeInForce: String,
    val type: String,
    val side: String
)