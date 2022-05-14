package com.portfolio.tracker.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.portfolio.tracker.core.Core
import com.portfolio.tracker.core.entity.Coin
import com.portfolio.tracker.core.entity.Holding
import com.portfolio.tracker.model.*
import com.portfolio.tracker.repository.CoinMarketRepository
import com.portfolio.tracker.repository.ExchangeRepository
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
import org.knowm.xchange.currency.Currency
import org.knowm.xchange.deribit.v2.dto.DeribitException
import org.knowm.xchange.dto.account.Wallet
import org.knowm.xchange.exceptions.ExchangeException
import org.knowm.xchange.ftx.FtxException
import org.knowm.xchange.kucoin.service.KucoinApiException
import org.knowm.xchange.okex.v5.dto.OkexException
import java.io.IOException
import java.math.BigDecimal
import java.util.*

internal class ExchangeViewModel(private val exchangeType: ExchangeTypeItem) : ViewModel() {

    val loadingState = MutableLiveData<LoadingState>()
    val data = MutableLiveData<Map<String, Wallet>>()
    val tickers = MutableLiveData<String>()
    val isExchangeConnected = MutableLiveData<Boolean>()
    val isDisplayable = MutableLiveData<Boolean>()
    var portfolio: Portfolio? = null
    private var repository: CoinMarketRepository = CoinMarketRepository()
    private var exchangeRepository: ExchangeRepository = ExchangeRepository()
    private var exchangeData: Exchange? = null

    fun synchronize(context: Context) {
        when (exchangeType) {
            ExchangeType.GATE_IO -> synchronizeGateio(context)
            else -> fetchExchange(context)
        }
    }

    private fun fetchExchange(context: Context) {
        loadingState.postValue(LoadingState.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ConnectUtils.getExchange(context, exchangeType)
                exchange?.let {
                    val set = mutableSetOf<Holding>()
                    exchange.accountService.accountInfo.wallets.entries.forEach {
                        it.value.balances.forEach { balance ->
                            balance.value.apply {
                                if (this.total > BigDecimal(0.0) && balance.value.currency != Currency.USD) {
                                    set.add(
                                        Holding(
                                            "",
                                            this.currency.currencyCode,
                                            this.total.toDouble(),
                                            this.available.toDouble(),
                                            this.frozen.toDouble(),
                                            this.borrowed.toDouble(),
                                            this.loaned.toDouble(),
                                            this.withdrawing.toDouble(),
                                            this.depositing.toDouble(),
                                            Coin(
                                                this.currency.displayName,
                                                this.currency.currencyCode
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Core.database.holdingDao().insert(set.toList())
                    tickers.postValue(
                        Core.database.holdingDao().getHoldings()
                            .joinToString(separator = ",") { holding -> holding.coin.currencyCode })
                } ?: run {
                    isExchangeConnected.postValue(false)
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
                apiKey?.let {
                    secretKey?.let {
                        val result =
                            apiInstance.listSpotAccounts().currency("usdt").execute()
                        result.forEach {
                            Log.e("Gate.io", "${it.available}")
                        }
                    }
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

    fun isDisplayable() {
        CoroutineScope(Dispatchers.IO).launch {
            isExchangeConnected.postValue(true)
            isDisplayable.postValue(Core.database.holdingDao().getHoldings().isNotEmpty())
        }
    }

    fun geQuotes(symbols: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getQuotes(symbols)
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        portfolio = buildPortfolioData()
                        loadingState.postValue(LoadingState.LOADED)
                        isDisplayable()
                    }
                    Resource.Status.ERROR -> loadingState.postValue(LoadingState.error(response.message))
                }
            } catch (e: IOException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    //TODO Refactoring / Configure balance limit
    private fun buildPortfolioData(): Portfolio {
        val walletsData = mutableListOf<WalletData>()
        val balances = mutableListOf<BalanceData>()
        Core.database.holdingDao().getHoldings().forEach { holding ->
            Core.database.coinInfoDao().getCoinsInfo().forEach {
                if (it.symbol == holding.coin.currencyCode) {
                    balances.add(
                        BalanceData(
                            it.id,
                            BigDecimal(holding.total),
                            BigDecimal(holding.available),
                            BigDecimal(holding.frozen),
                            BigDecimal(holding.borrowed),
                            BigDecimal(holding.loaned),
                            BigDecimal(holding.withdrawing),
                            BigDecimal(holding.depositing),
                            Date(),
                            Currency.getInstance(holding.coin.currencyCode),
                            it.coinQuote.price
                        )
                    )
                }
            }
        }

        walletsData.add(
            WalletData(
                "WalletId",
                balances,
                "beta",
                setOf()
            )
        )

        return Portfolio(
            "username",
            exchangeType, Date(),
            walletsData
        )
    }

    @Suppress("UNCHECKED_CAST")
    class ExchangeViewModelFactory(private val exchangeType: ExchangeTypeItem) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ExchangeViewModel(exchangeType) as T
        }
    }
}

