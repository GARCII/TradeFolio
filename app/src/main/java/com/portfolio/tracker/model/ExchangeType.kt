package com.portfolio.tracker.model

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.portfolio.tracker.R
import com.portfolio.tracker.fragment.HoldingListFragment
import org.knowm.xchange.BaseExchange
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
import java.io.Serializable

enum class ExchangeType : ExchangeTypeItem {
    FTX, BINANCE, DERIBIT, KUCOIN, ASCENDEX, GATE_IO, HUOBI, KRAKEN, OKEX, COINBASE, BITFINEX, BITMEX, BITTREX, CRYPTO_COM;

    companion object {
        fun sanitizeExchanges() =
            values().toList().filter { it.isSyncAuthorized() }.sortedBy { it.name }
    }

    override fun getName(context: Context) = when (this) {
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

    override fun getApiPrefKey() = "${this.name}_api_key"

    override fun getSecretPrefKey() = "${this.name}_secret_key"

    override fun getImageResource(context: Context) = when (this) {
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

    override fun getSpecificParamItem() = when (this) {
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

    override fun getExchangeReference(): Class<out BaseExchange>? = when (this) {
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

    override fun isSyncAuthorized() = when (this) {
        FTX, ASCENDEX, DERIBIT, BITMEX, BITTREX, KUCOIN, GATE_IO, OKEX, HUOBI, BINANCE, COINBASE -> true
        KRAKEN, BITFINEX, CRYPTO_COM -> false
    }

    override fun getFragment() = HoldingListFragment.newInstance(this)
}

enum class SpecificExchangeParamType {
    ACCOUNT_GROUP, PASSPHRASE;

    fun getHeaderParam() = when (this) {
        ACCOUNT_GROUP -> "account-group"
        PASSPHRASE -> "passphrase"
    }

    fun getPrefKey(exchangeType: ExchangeTypeItem) = when (this) {
        ACCOUNT_GROUP -> "account-group"
        PASSPHRASE -> "passphrase"
    }.let {
        "${(exchangeType as ExchangeType).name}_$it"
    }

    fun getHint(context: Context) = when (this) {
        ACCOUNT_GROUP -> R.string.tf_exchange_param_account_group
        PASSPHRASE -> R.string.tf_exchange_param_passphrase
    }.let {
        context.getString(it)
    }
}

interface ExchangeTypeItem: Serializable {
    fun getName(context: Context): String
    fun getImageResource(context: Context) : Drawable?
    fun getFragment(): Fragment
    fun isSyncAuthorized(): Boolean
    fun getSpecificParamItem(): SpecificExchangeParamType?
    fun getExchangeReference(): Class<out BaseExchange>?
    fun getSecretPrefKey(): String?
    fun getApiPrefKey(): String?
}
