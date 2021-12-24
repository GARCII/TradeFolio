package com.portfolio.tracker.model

import android.content.Context
import androidx.core.content.ContextCompat
import com.portfolio.tracker.R

enum class ExchangeType {
    FTX, BINANCE, DERIBIT, ASCENDEX, GATE_IO, HUOBI, KRAKEN, OKEX, COINBASE, BITFINEX, BITMEX, BITTREX, CRYPTO_COM;

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
    }.let {
        context.getString(it)
    }

    fun getApiPrefKey() = when (this) {
        FTX -> "ftx"
        BINANCE -> "binance"
        DERIBIT -> "deribit"
        ASCENDEX -> "ascendex"
        GATE_IO -> "gate_io"
        HUOBI -> "huobi"
        KRAKEN -> "kraken"
        OKEX -> "okex"
        COINBASE -> "coinbase"
        BITFINEX -> "bitfinex"
        BITMEX -> "bitmex"
        BITTREX -> "bittrex"
        CRYPTO_COM -> "crypto_com"
    }.let {
        "${it}_api_key"
    }

    fun getSecretPrefKey() = when (this) {
        FTX -> "ftx"
        BINANCE -> "binance"
        DERIBIT -> "deribit"
        ASCENDEX -> "ascendex"
        GATE_IO -> "gate_io"
        HUOBI -> "huobi"
        KRAKEN -> "kraken"
        OKEX -> "okex"
        COINBASE -> "coinbase"
        BITFINEX -> "bitfinex"
        BITMEX -> "bitmex"
        BITTREX -> "bittrex"
        CRYPTO_COM -> "crypto_com"
    }.let {
        "${it}_secret_key"
    }

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
    }.let {
        ContextCompat.getDrawable(context, it)
    }
}