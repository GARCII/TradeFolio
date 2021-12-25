package com.portfolio.tracker.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tracker.model.ExchangeType
import com.portfolio.tracker.util.*
import io.gate.gateapi.ApiException
import io.gate.gateapi.Configuration
import io.gate.gateapi.GateApiException
import io.gate.gateapi.api.SpotApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.knowm.xchange.ascendex.AscendexException
import org.knowm.xchange.binance.dto.BinanceException
import org.knowm.xchange.bitmex.BitmexException
import org.knowm.xchange.bittrex.dto.BittrexException
import org.knowm.xchange.coinbase.dto.CoinbaseException
import org.knowm.xchange.deribit.v2.dto.DeribitException
import org.knowm.xchange.dto.account.Wallet
import org.knowm.xchange.exceptions.ExchangeException
import org.knowm.xchange.ftx.FtxException
import org.knowm.xchange.kucoin.service.KucoinApiException
import org.knowm.xchange.okex.v5.dto.OkexException
import java.io.IOException

internal class ExchangeViewModel : ViewModel() {

    val loadingState = MutableLiveData<LoadingState>()
    val data = MutableLiveData<Map<String, Wallet>>()

    fun connectPortfolio(context: Context, exchangeType: ExchangeType) {
        when (exchangeType) {
            ExchangeType.GATE_IO -> connectGateio(context)
            else -> connectXchange(context, exchangeType)
        }
    }

    private fun connectXchange(context: Context, exchangeType: ExchangeType) {
        loadingState.postValue(LoadingState.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ConnectUtils.getExchange(context, exchangeType)
                exchange?.let {
                    data.postValue(it.accountService.accountInfo.wallets)
                    loadingState.postValue(LoadingState.LOADED)
                } ?: loadingState.postValue(LoadingState.error("Exchange not found"))
            } catch (e: AscendexException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: BinanceException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: BitmexException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: BittrexException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: CoinbaseException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: DeribitException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: FtxException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: KucoinApiException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: OkexException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: ExchangeException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            } catch (e: IOException) {
                loadingState.postValue(LoadingState.error("${e.stackTrace} ${e.message}"))
            }
        }
    }

    private fun connectGateio(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiClient = Configuration.getDefaultApiClient()
            val sharedPreferencesUtils = TradeFolioSharedPreferencesUtils(context)
            val apiKey = sharedPreferencesUtils.getString(ExchangeType.GATE_IO.getApiPrefKey())
            val secretKey = sharedPreferencesUtils.getString(ExchangeType.GATE_IO.getSecretPrefKey())
            apiClient.basePath = GATE_IO_BASE_URL
            apiClient.setApiKeySecret(apiKey, secretKey)
            val apiInstance = SpotApi(apiClient)
            try {
                val result =
                    apiInstance.listSpotAccounts().currency("usdt").execute()
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

