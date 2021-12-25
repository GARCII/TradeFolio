package com.portfolio.tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tracker.util.ExchangeUtils
import com.portfolio.tracker.util.GATE_API_KEY
import com.portfolio.tracker.util.GATE_API_SECRET
import com.portfolio.tracker.util.LoadingState
import io.gate.gateapi.ApiException
import io.gate.gateapi.Configuration
import io.gate.gateapi.GateApiException
import io.gate.gateapi.api.SpotApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.knowm.xchange.ascendex.AscendexException
import org.knowm.xchange.binance.dto.BinanceException
import org.knowm.xchange.bitfinex.dto.BitfinexException
import org.knowm.xchange.bitmex.BitmexException
import org.knowm.xchange.bittrex.dto.BittrexException
import org.knowm.xchange.coinbase.dto.CoinbaseException
import org.knowm.xchange.deribit.v2.dto.DeribitException
import org.knowm.xchange.dto.account.Wallet
import org.knowm.xchange.exceptions.ExchangeException
import org.knowm.xchange.ftx.FtxException
import org.knowm.xchange.kucoin.service.KucoinApiException
import org.knowm.xchange.okex.v5.dto.OkexException

internal class ExchangeViewModel : ViewModel() {

    val loadingState = MutableLiveData<LoadingState>()
    val balanceData = MutableLiveData<Map<String, Wallet>>()

    fun fetchBinanceData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getBinanceExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: BinanceException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    fun fetchHuobiData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getHuobiExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: ExchangeException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    fun fetchFtxData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getFtxExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: FtxException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    fun fetchAscendexData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getAscendexExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: AscendexException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    fun fetchKucoinData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getKucoinExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: KucoinApiException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    fun fetchKrakenData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getKrakenExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: ExchangeException) {
                loadingState.postValue(LoadingState.error("${e.message}"))
            }
        }
    }

    fun fetchBittrexData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getBittrexExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: BittrexException) {
                loadingState.postValue(LoadingState.error("${e.message}"))
            }
        }
    }

    fun fetchOkexData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getOkexExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: OkexException) {
                loadingState.postValue(LoadingState.error("${e.message}"))
            }
        }
    }

    fun fetchCoinbaseData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getCoinbaseExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: CoinbaseException) {
                loadingState.postValue(LoadingState.error("${e.message}"))
            }
        }
    }

    fun fetchBitfinexData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getBitfinexExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: BitfinexException) {
                loadingState.postValue(LoadingState.error("${e.message}"))
            }
        }
    }

    fun fetchBitmexData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getBitmexExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: BitmexException) {
                loadingState.postValue(LoadingState.error("${e.message}"))
            }
        }
    }

    fun fetchDeribitData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getDeribitExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: DeribitException) {
                loadingState.postValue(LoadingState.error("${e.message}"))
            }
        }
    }

    fun fetchGateioData() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiClient = Configuration.getDefaultApiClient()
            apiClient.basePath = "https://api.gateio.ws/api/v4"
            apiClient.setApiKeySecret(GATE_API_KEY, GATE_API_SECRET)
            val apiInstance = SpotApi(apiClient)
            val settle = "usdt" // Currency
            try {
                val result = apiInstance.listSpotAccounts().currency(settle).execute()
                result.forEach {
                    Log.e("Gate.io", "${it.available}")
                }
            } catch (e: GateApiException) {
                Log.e("Gate.io", "${e.errorDetail} ${e.errorLabel} ${e.errorMessage}")
            } catch (e: ApiException) {
                Log.e("Gate.io", "${e.code} ${e.responseBody} ${e.responseHeaders}")
            }
        }
    }

    fun fetchBybitData() {
        CoroutineScope(Dispatchers.IO).launch {
        }
    }
}

