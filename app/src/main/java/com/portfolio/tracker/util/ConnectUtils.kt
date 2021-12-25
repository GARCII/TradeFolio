package com.portfolio.tracker.util

import android.content.Context
import com.portfolio.tracker.model.ExchangeType
import org.knowm.xchange.Exchange
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.ExchangeSpecification

object ConnectUtils {

    //TODO Runtime exception "Ticker not valid"
    //TODO Kraken Error - EGeneral:Internal error
    fun getExchange(context: Context, exchangeType: ExchangeType): Exchange? {
        exchangeType.getClassInstance()?.let {
            val specification = ExchangeSpecification(it)
            val sharedPreferencesUtils = TradeFolioSharedPreferencesUtils(context)
            specification.apply {
                apiKey = sharedPreferencesUtils.getString(exchangeType.getApiPrefKey())
                secretKey = sharedPreferencesUtils.getString(exchangeType.getSecretPrefKey())
                exchangeType.getSpecificParamItem()?.let { parameter ->
                    val key = parameter.getKey()
                    setExchangeSpecificParametersItem(
                        key,
                        sharedPreferencesUtils.getString(key)
                    )
                }
            }
            return ExchangeFactory.INSTANCE.createExchange(specification)
        }
        return null
    }
}