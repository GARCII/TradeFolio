package com.portfolio.tracker.model

data class Order(
    val symbol: String,
    val orderId: String,
    val clientOrderId: String,
    val price: String,
    val origQty: String,
    val executedQty: String,
    val status: String,
    val type: String,
    val side: OrderSide,
    val isWorking: Boolean,
    val time: Long? = null,
    val updateTime: Long? = null
)

enum class OrderSide {
    BUY, SELL
}