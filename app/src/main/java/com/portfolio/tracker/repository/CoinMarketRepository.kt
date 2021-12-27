package com.portfolio.tracker.repository

import com.portfolio.tracker.model.CoinMarket
import com.portfolio.tracker.model.CoinMarketResponse
import com.portfolio.tracker.model.PriceQuote
import com.portfolio.tracker.remote.Client
import com.portfolio.tracker.util.Resource
import org.knowm.xchange.currency.Currency

class CoinMarketRepository {

    suspend fun getQuote(symbol: String): Resource<List<CoinMarket>> {
        val api = Client.getCoinMarketApi()
        val result = api.getCurrencies(symbol = symbol)
        return if (result.isSuccessful && result.body() != null) {
            val coinMarketResponse =
                convertResponseToCoinMarket(result.body() as CoinMarketResponse)
            Resource(Resource.Status.SUCCESS, coinMarketResponse)
        } else {
            Resource(Resource.Status.ERROR, null, result.message())
        }
    }

    private fun convertResponseToCoinMarket(coinMarketResponse: CoinMarketResponse): List<CoinMarket> {
        val map = mutableMapOf<Currency, PriceQuote>()
        return coinMarketResponse.data.map {
            it.value.priceQuotes.forEach { priceQuote ->
                map[Currency.getInstanceNoCreate(it.key)] = PriceQuote(
                    "${priceQuote.value.price}",
                    "${priceQuote.value.dayVolume}",
                    "${priceQuote.value.marketCap}",
                    "${priceQuote.value.hourChange}",
                    "${priceQuote.value.dayChange}",
                    "${priceQuote.value.weekChange}"
                )
            }

            CoinMarket(
                it.value.id,
                it.value.name,
                it.value.symbol,
                it.value.slug,
                it.value.rank,
                "${it.value.circulationSupply}",
                "${it.value.totalSupply}",
                "${it.value.maxSupply}",
                map
            )
        }
    }
}