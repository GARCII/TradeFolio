package com.portfolio.tracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tracker.model.Order
import com.portfolio.tracker.repository.BinanceRepository
import com.portfolio.tracker.util.KucoinUtils
import com.portfolio.tracker.util.LoadingState
import com.portfolio.tracker.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.knowm.xchange.dto.account.Wallet
import java.io.IOException

internal class BinanceViewModel : ViewModel() {

    val loadingState = MutableLiveData<LoadingState>()
    val balanceData = MutableLiveData<Map<String, Wallet>>()
    val orderData = MutableLiveData<List<Order>>()
    val cancelOrderData = MutableLiveData<Order>()
    val createOrderData = MutableLiveData<Order>()
    private var repository = BinanceRepository()

    fun fetchAccountData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = KucoinUtils.getExchange()
                val accountService = exchange.accountService
                balanceData.postValue(accountService.accountInfo.wallets)
            } catch (e: IOException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    fun fetchOpenOrders() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loadingState.postValue(LoadingState.LOADING)
                val response = repository.getOpenOrders()
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        orderData.postValue(response.data!!)
                        loadingState.postValue(LoadingState.LOADED)
                    }
                    Resource.Status.ERROR -> loadingState.postValue(LoadingState.error(response.message))
                }
            } catch (e: IOException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    fun cancelOrder(orderId: String, symbol: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loadingState.postValue(LoadingState.LOADING)
                val response = repository.cancelOrder(orderId, symbol)
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        response.data?.let {
                            cancelOrderData.postValue(it)
                            loadingState.postValue(LoadingState.LOADED)
                        }
                    }
                    Resource.Status.ERROR -> loadingState.postValue(LoadingState.error(response.message))
                }
            } catch (e: IOException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    fun createOrder(symbol: String, side: String, type: String, quantity: Float, price: Float) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loadingState.postValue(LoadingState.LOADING)
                val response = repository.createOrder(symbol, side, type, quantity, price)
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        response.data?.let {
                            createOrderData.postValue(it)
                            loadingState.postValue(LoadingState.LOADED)
                        }
                    }
                    Resource.Status.ERROR -> loadingState.postValue(LoadingState.error(response.message))
                }
            } catch (e: IOException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }
}