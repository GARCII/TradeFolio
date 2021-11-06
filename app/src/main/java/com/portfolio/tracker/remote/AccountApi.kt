package com.portfolio.tracker.remote

import com.portfolio.tracker.model.AssetResultResponse
import com.portfolio.tracker.model.CancelOrderResponse
import com.portfolio.tracker.model.OrderResultPost
import com.portfolio.tracker.model.OrderResultResponse
import com.portfolio.tracker.util.*
import retrofit2.Response
import retrofit2.http.*

interface AccountApi {
    @GET(DAILY_BALANCE)
    @Headers("Accept: application/json")
    suspend fun getDailyBalance(
        @Header("X-MBX-APIKEY") apiKey: String = API_KEY,
        @Query("type") type: String,
        @Query("timestamp") timestamp: String,
        @Query("signature") signature: String
    ): Response<AssetResultResponse>

    @GET(OPEN_ORDERS)
    suspend fun getOpenOrders(
        @Header("X-MBX-APIKEY") apiKey: String = API_KEY,
        @Query("timestamp") timestamp: String,
        @Query("signature") signature: String
    ): Response<List<OrderResultResponse>>

    @DELETE(ORDER)
    suspend fun cancelOrder(
        @Header("X-MBX-APIKEY") apiKey: String = API_KEY,
        @Query("symbol") symbol: String,
        @Query("orderId") orderId: String,
        @Query("timestamp") timestamp: String,
        @Query("signature") signature: String,
    ): Response<OrderResultResponse>

    @POST(ORDER)
    suspend fun createOrder(
        @Header("X-MBX-APIKEY") apiKey: String = API_KEY,
        @Query("symbol") symbol: String,
        @Query("side") side: String,
        @Query("type") type: String,
        @Query("timeInForce") timeInForce: String,
        @Query("quantity") quantity: String,
        @Query("price") price: String,
        @Query("timestamp") timestamp: String,
        @Query("signature") signature: String,
    ): Response<OrderResultPost>
}
