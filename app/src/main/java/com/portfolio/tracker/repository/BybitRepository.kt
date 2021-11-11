package com.portfolio.tracker.repository

import com.portfolio.tracker.remote.BybitResponse
import com.portfolio.tracker.remote.Client
import com.portfolio.tracker.util.FTX_API_SECRET
import com.portfolio.tracker.util.Resource

internal class BybitRepository {
    private val api = Client.getBybitApi()

    suspend fun getDailySnapshot(): Resource<BybitResponse> {
        val currentMilli = System.currentTimeMillis().toString()
        val data = "${currentMilli}GET/account"
        val encodedData = String(data.encodeToByteArray(), charset("UTF-8"))
        val signature = FTX_API_SECRET
        val accountResult = api.getDailyBalance(
            timestamp = currentMilli,
            signature = signature
        )

        return if (accountResult.isSuccessful && accountResult.body() != null) {
            val account = accountResult.body() as BybitResponse
            Resource(Resource.Status.SUCCESS, account)
        } else {
            Resource(Resource.Status.ERROR, null, accountResult.message())
        }
    }
}