package com.portfolio.tracker.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.portfolio.tracker.model.*
import com.portfolio.tracker.util.*
import io.gate.gateapi.ApiException
import io.gate.gateapi.Configuration
import io.gate.gateapi.GateApiException
import io.gate.gateapi.api.SpotApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.knowm.xchange.Exchange
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
import java.math.BigDecimal
import java.util.*

internal class ExchangeViewModel(private val exchangeType: ExchangeType) : ViewModel() {

    val loadingState = MutableLiveData<LoadingState>()
    val data = MutableLiveData<Map<String, Wallet>>()
    val isExchangeConned = MutableLiveData<Boolean>()
    var portfolio: Portfolio? = null

    fun synchronize(context: Context) {
        when (exchangeType) {
            ExchangeType.GATE_IO -> synchronizeGateio(context)
            else -> synchronizeXchange(context)
        }
    }

    private fun synchronizeXchange(context: Context) {
        loadingState.postValue(LoadingState.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ConnectUtils.getExchange(context, exchangeType)
                exchange?.let {
                    portfolio = buildPortfolioData(exchange)
                    isExchangeConned.postValue(true)
                    loadingState.postValue(LoadingState.LOADED)
                } ?: run {
                    isExchangeConned.postValue(true)
                    loadingState.postValue(LoadingState.error("Exchange not found"))
                }

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

    private fun synchronizeGateio(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiClient = Configuration.getDefaultApiClient()
            val sharedPreferencesUtils = TradeFolioSharedPreferencesUtils(context)
            val apiKey = sharedPreferencesUtils.getString(ExchangeType.GATE_IO.getApiPrefKey())
            val secretKey =
                sharedPreferencesUtils.getString(ExchangeType.GATE_IO.getSecretPrefKey())
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

    fun getHoldings(): MutableSet<BalanceData> {
        val holdings = mutableSetOf<BalanceData>()
        portfolio?.wallets?.forEach {
            holdings.addAll(it.balances)
        }
        return holdings.toSortedSet(compareBy { it.currency.displayName })
    }

    fun isDisplayable(): Boolean {
        var count = 0
        portfolio?.wallets?.forEach {
            count += it.balances.size
        }
        return count > 0
    }

    //TODO Refactoring / Configure balance limit
    private fun buildPortfolioData(exchange: Exchange): Portfolio {
        val walletsData = mutableListOf<WalletData>()
        val balances = mutableListOf<BalanceData>()
        exchange.accountService.accountInfo.wallets.entries.forEach {
            it.value.balances.forEach { balance ->
                balance.value.apply {
                    if (this.total > BigDecimal(0)) {
                        balances.add(
                            BalanceData(
                                this.total,
                                this.available,
                                this.frozen,
                                this.borrowed,
                                this.loaned,
                                this.withdrawing,
                                this.depositing,
                                Date(),
                                this.currency
                            )
                        )
                    }
                }
            }

            walletsData.add(
                WalletData(
                    it.key,
                    balances,
                    "beta",
                    it.value.features
                )
            )
        }
        walletsData.forEach {
            it.balances.forEach { balance ->
                Log.e("TEST", "${balance.currency.currencyCode}")
            }
        }

        return Portfolio(
            exchange.accountService.accountInfo.username,
            exchangeType, Date(),
            walletsData
        )
    }

    @Suppress("UNCHECKED_CAST")
    class ExchangeViewModelFactory(private val exchangeType: ExchangeType) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ExchangeViewModel(exchangeType) as T
        }
    }
}

