package com.portfolio.tracker.repository

import android.net.Uri
import android.util.Log
import com.portfolio.tracker.model.*
import com.portfolio.tracker.remote.Client
import com.portfolio.tracker.util.Signature
import com.portfolio.tracker.util.API_SECRET
import com.portfolio.tracker.util.Resource
import java.util.ArrayList

internal class BinanceRepository {
    private val api = Client.getBinanceApi()

    suspend fun getDailySnapshot(): Resource<List<Asset>> {
        val currentMilli = System.currentTimeMillis().toString()
        val parameters = mutableMapOf<String, String>()
        parameters["type"] = "SPOT"
        parameters["timestamp"] = currentMilli
        val queryPath = buildQueryParameters(parameters)
        val signature = Signature.getSignature(queryPath, API_SECRET)
        val accountResult = api.getDailyBalance(
            type = "SPOT",
            timestamp = currentMilli,
            signature = signature
        )

        return if (accountResult.isSuccessful && accountResult.body() != null) {
            val assetList =
                convertAccountResponseToAsset(accountResult.body() as AssetResultResponse)
            Resource(Resource.Status.SUCCESS, assetList)
        } else {
            Resource(Resource.Status.ERROR, ArrayList(), accountResult.message())
        }
    }

    suspend fun getOpenOrders(): Resource<List<Order>> {
        val currentMilli = System.currentTimeMillis().toString()
        val parameters = mutableMapOf<String, String>()
        parameters["timestamp"] = currentMilli
        val queryPath = buildQueryParameters(parameters)
        val signature = Signature.getSignature(queryPath, API_SECRET)
        val orderResult = api.getOpenOrders(
            timestamp = currentMilli,
            signature = signature
        )
        return if (orderResult.isSuccessful && orderResult.body() != null) {
            val orderList =
                convertOpenOrderResponseToOrder(orderResult.body() as List<OrderResultResponse>)
            Resource(Resource.Status.SUCCESS, orderList)
        } else {
            Resource(Resource.Status.ERROR, ArrayList(), orderResult.message())
        }
    }

    suspend fun cancelOrder(orderId: String, symbol: String): Resource<Order> {
        val currentMilli = System.currentTimeMillis().toString()
        val parameters = mutableMapOf<String, String>()
        parameters["symbol"] = symbol
        parameters["orderId"] = orderId
        parameters["timestamp"] = currentMilli
        val queryPath = buildQueryParameters(parameters)
        val signature = Signature.getSignature(queryPath, API_SECRET)
        val cancelOrderResult = api.cancelOrder(
            symbol = symbol,
            orderId = orderId,
            timestamp = currentMilli,
            signature = signature,
        )
        return if (cancelOrderResult.isSuccessful && cancelOrderResult.body() != null) {
            val canceledOrder = cancelOrderResult.body() as OrderResultResponse
            Resource(Resource.Status.SUCCESS,  Order(
                canceledOrder.symbol,
                canceledOrder.orderId.toString(),
                canceledOrder.clientOrderId,
                canceledOrder.price,
                canceledOrder.origQty,
                canceledOrder.executedQty,
                canceledOrder.status,
                canceledOrder.type,
                OrderSide.valueOf(canceledOrder.side),
                canceledOrder.isWorking,
                canceledOrder.time,
                canceledOrder.updateTime
            ))
        } else {
            Resource(Resource.Status.ERROR, null,cancelOrderResult.message())
        }
    }

    suspend fun createOrder(
        symbol: String,
        side: String,
        type: String,
        quantity: Float,
        price: Float,
    ): Resource<Order> {
        val currentMilli = System.currentTimeMillis().toString()
        val parameters = mutableMapOf<String, String>()
        parameters["symbol"] = symbol
        parameters["side"] = side
        parameters["type"] = type
        parameters["timeInForce"] = "GTC"
        parameters["quantity"] = quantity.toString()
        parameters["price"] = price.toString()
        parameters["timestamp"] = currentMilli
        val queryPath = buildQueryParameters(parameters)
        Log.e("url", queryPath)
        val signature = Signature.getSignature(queryPath, API_SECRET)
        val createOrder = api.createOrder(
            symbol = symbol,
            side = side,
            type = type,
            timeInForce= "GTC",
            quantity = quantity.toString(),
            price= price.toString(),
            timestamp = currentMilli,
            signature = signature,
        )

        return if (createOrder.isSuccessful && createOrder.body() != null) {
            val createdOrder = createOrder.body() as OrderResultPost
            Resource(Resource.Status.SUCCESS,  Order(
                createdOrder.symbol,
                createdOrder.orderId.toString(),
                createdOrder.clientOrderId,
                createdOrder.price,
                createdOrder.origQty,
                createdOrder.executedQty,
                createdOrder.status,
                createdOrder.type,
                OrderSide.valueOf(createdOrder.side), true))
        } else {
            Log.e("url", createOrder.errorBody().toString())
            Resource(Resource.Status.ERROR, null, createOrder.errorBody().toString())
        }
    }

    private fun convertOpenOrderResponseToOrder(orderResultResponse: List<OrderResultResponse>): List<Order> =
        orderResultResponse.map {
            Order(
                it.symbol,
                it.orderId.toString(),
                it.clientOrderId,
                it.price,
                it.origQty,
                it.executedQty,
                it.status,
                it.type,
                OrderSide.valueOf(it.side),
                it.isWorking,
                it.time,
                it.updateTime
            )
        }

    private fun convertAccountResponseToAsset(assetResultResponse: AssetResultResponse): List<Asset> {
        val assetList = assetResultResponse.snapshotVos[0]
        return assetList.data.balances.map {
            Asset(
                it.asset,
                it.free
            )
        }
    }

    private fun buildQueryParameters(parameters: MutableMap<String, String>?): String {
        var buildUrl = ""
        if (!parameters.isNullOrEmpty()) {
            val keySet = parameters.keys
            for (key in keySet) {
                buildUrl += key + "=" + Uri.encode(parameters[key]) + "&"
            }
            buildUrl = buildUrl.substring(0, buildUrl.length - 1)
        }
        return buildUrl
    }
}