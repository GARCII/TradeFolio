package com.portfolio.tracker.repository

import com.portfolio.tracker.core.Core
import com.portfolio.tracker.core.entity.CoinInfo
import com.portfolio.tracker.core.entity.CoinQuote
import com.portfolio.tracker.model.CoinMarket
import com.portfolio.tracker.model.CoinMarketResponse
import com.portfolio.tracker.model.PriceQuote
import com.portfolio.tracker.remote.Client
import com.portfolio.tracker.util.Resource

class CoinMarketRepository {

    suspend fun getQuotes(symbols: String): Resource<List<CoinMarket>> {
        val api = Client.getCoinMarketApi()
        val result = api.getCurrencies(symbols = symbols)
        return if (result.isSuccessful && result.body() != null) {
            val coinMarketResponse = convertResponseToCoinMarket(result.body() as CoinMarketResponse)
            val coinList = coinMarketResponse.map {
                CoinInfo(
                    it.id,
                    it.name,
                    it.symbol,
                    it.slug,
                    it.rank,
                    it.circulationSupply,
                    it.totalSupply,
                    it.maxSupply,
                    CoinQuote(
                        it.priceQuotes.price,
                        it.priceQuotes.dayVolume,
                        it.priceQuotes.marketCap,
                        it.priceQuotes.hourChange,
                        it.priceQuotes.dayChange,
                        it.priceQuotes.weekChange,
                    )
                )
            }
            Core.database.coinInfoDao().insert(coinList)
            Resource(Resource.Status.SUCCESS, coinMarketResponse)
        } else {
            Resource(Resource.Status.ERROR, listOf(), result.errorBody().toString())
        }
    }

    private fun convertResponseToCoinMarket(coinMarketResponse: CoinMarketResponse): List<CoinMarket> {
        val coinList = mutableListOf<CoinMarket>()
        coinMarketResponse.data.forEach {
            it.value.priceQuotes.forEach { priceQuote ->
                coinList.add(
                    CoinMarket(
                        it.value.id,
                        it.value.name,
                        it.value.symbol,
                        it.value.slug,
                        it.value.rank,
                        "${it.value.circulationSupply}",
                        "${it.value.totalSupply}",
                        "${it.value.maxSupply}",
                        PriceQuote(
                            "${priceQuote.value.price}",
                            "${priceQuote.value.dayVolume}",
                            "${priceQuote.value.marketCap}",
                            "${priceQuote.value.hourChange}",
                            "${priceQuote.value.dayChange}",
                            "${priceQuote.value.weekChange}"
                        )
                    )
                )
            }
        }
        return coinList
    }
}