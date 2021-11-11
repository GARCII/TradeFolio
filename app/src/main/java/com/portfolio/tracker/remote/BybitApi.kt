package com.portfolio.tracker.remote


import com.portfolio.tracker.util.*
import retrofit2.Response
import retrofit2.http.*

interface BybitApi {
    @GET(ACCOUNT_FTX)
    @Headers("Accept: application/json")
    suspend fun getDailyBalance(
        @Query("api_key") apiKey: String = FTX_API_KEY,
        @Query("timestamp") signature: String,
        @Query("sign") timestamp: String
    ): Response<BybitResponse>
}

data class BybitResponse(
    val message :String
)