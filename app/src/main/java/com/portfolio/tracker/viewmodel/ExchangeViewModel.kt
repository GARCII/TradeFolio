package com.portfolio.tracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kucoin.sdk.exception.KucoinApiException
import com.portfolio.tracker.util.ExchangeUtils
import com.portfolio.tracker.util.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.knowm.xchange.dto.account.Wallet

internal class ExchangeViewModel : ViewModel() {

    val loadingState = MutableLiveData<LoadingState>()
    val balanceData = MutableLiveData<Map<String, Wallet>>()

    fun fetchBinanceData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getBinanceExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: KucoinApiException) {
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
            } catch (e: KucoinApiException) {
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
            } catch (e: KucoinApiException) {
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
            } catch (e: KucoinApiException) {
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

    fun fetchGateioData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ExchangeUtils.getGateioExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: KucoinApiException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }
}