package com.portfolio.tracker.model

import android.content.Context
import androidx.core.content.ContextCompat
import com.portfolio.tracker.R
import org.knowm.xchange.ascendex.AscendexExchange
import org.knowm.xchange.binance.BinanceExchange
import org.knowm.xchange.bitfinex.BitfinexExchange
import org.knowm.xchange.bitmex.BitmexExchange
import org.knowm.xchange.bittrex.BittrexExchange
import org.knowm.xchange.coinbase.v2.CoinbaseExchange
import org.knowm.xchange.deribit.v2.DeribitExchange
import org.knowm.xchange.ftx.FtxExchange
import org.knowm.xchange.huobi.HuobiExchange
import org.knowm.xchange.kraken.KrakenExchange
import org.knowm.xchange.kucoin.KucoinExchange
import org.knowm.xchange.okex.v5.OkexExchange

enum class ExchangeType {
    FTX, BINANCE, DERIBIT, KUCOIN, ASCENDEX, GATE_IO, HUOBI, KRAKEN, OKEX, COINBASE, BITFINEX, BITMEX, BITTREX, CRYPTO_COM;

    companion object {
        fun sanitizeExchanges() =
            values().toList().filter { it.isSyncAuthorized() }.sortedBy { it.name }
    }

    fun getName(context: Context) = when (this) {
        FTX -> R.string.tf_exchange_name_ftx
        BINANCE -> R.string.tf_exchange_name_binance
        DERIBIT -> R.string.tf_exchange_name_deribit
        ASCENDEX -> R.string.tf_exchange_name_ascendex
        GATE_IO -> R.string.tf_exchange_name_gate_io
        HUOBI -> R.string.tf_exchange_name_huobi
        KRAKEN -> R.string.tf_exchange_name_kraken
        OKEX -> R.string.tf_exchange_name_okex
        COINBASE -> R.string.tf_exchange_name_coinbase
        BITFINEX -> R.string.tf_exchange_name_bitfinex
        BITMEX -> R.string.tf_exchange_name_bitmex
        BITTREX -> R.string.tf_exchange_name_bittrex
        CRYPTO_COM -> R.string.tf_exchange_name_crypto_com
        KUCOIN -> R.string.tf_exchange_name_kucoin
    }.let {
        context.getString(it)
    }

    fun getApiPrefKey() = "${this.name}_api_key"

    fun getSecretPrefKey() = "${this.name}_secret_key"

    fun getResourceId(context: Context) = when (this) {
        FTX -> R.drawable.ftx
        BINANCE -> R.drawable.binance
        DERIBIT -> R.drawable.deribit
        ASCENDEX -> R.drawable.ascendex
        GATE_IO -> R.drawable.gateio
        HUOBI -> R.drawable.huobi
        KRAKEN -> R.drawable.kraken
        OKEX -> R.drawable.okex
        COINBASE -> R.drawable.coinbase
        BITFINEX -> R.drawable.bitfinex
        BITMEX -> R.drawable.bitmex
        BITTREX -> R.drawable.bittrex
        CRYPTO_COM -> R.drawable.cryptocom
        KUCOIN -> R.drawable.kucoin
    }.let {
        ContextCompat.getDrawable(context, it)
    }

    fun getSpecificParamItem() = when (this) {
        ASCENDEX -> SpecificExchangeParamType.ACCOUNT_GROUP
        KUCOIN, OKEX -> SpecificExchangeParamType.PASSPHRASE
        FTX,
        BINANCE,
        DERIBIT,
        GATE_IO,
        HUOBI,
        KRAKEN,
        COINBASE,
        BITFINEX,
        BITMEX,
        BITTREX,
        CRYPTO_COM -> null
    }

    fun getClassInstance() = when (this) {
        FTX -> FtxExchange::class.java
        BINANCE -> BinanceExchange::class.java
        DERIBIT -> DeribitExchange::class.java
        KUCOIN -> KucoinExchange::class.java
        ASCENDEX -> AscendexExchange::class.java
        HUOBI -> HuobiExchange::class.java
        KRAKEN -> KrakenExchange::class.java
        OKEX -> OkexExchange::class.java
        COINBASE -> CoinbaseExchange::class.java
        BITFINEX -> BitfinexExchange::class.java
        BITMEX -> BitmexExchange::class.java
        BITTREX -> BittrexExchange::class.java
        GATE_IO, CRYPTO_COM -> null
    }

    fun isSyncAuthorized() = when (this) {
        BINANCE, COINBASE, FTX, ASCENDEX, DERIBIT, BITMEX, BITTREX, KUCOIN, GATE_IO, OKEX, HUOBI -> true
        KRAKEN, BITFINEX, CRYPTO_COM -> false
    }
}

enum class SpecificExchangeParamType {
    ACCOUNT_GROUP, PASSPHRASE;

    fun getHeaderParam() = when (this) {
        ACCOUNT_GROUP -> "account-group"
        PASSPHRASE -> "passphrase"
    }

    fun getPrefKey(exchangeType: ExchangeType) = when (this) {
        ACCOUNT_GROUP -> "account-group"
        PASSPHRASE -> "passphrase"
    }.let {
        "${exchangeType.name}_$it"
    }

    fun getHint(context: Context) = when (this) {
        ACCOUNT_GROUP -> R.string.tf_exchange_param_account_group
        PASSPHRASE -> R.string.tf_exchange_param_passphrase
    }.let {
        context.getString(it)
    }
}
