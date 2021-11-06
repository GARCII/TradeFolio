package com.portfolio.tracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tracker.model.Asset
import com.portfolio.tracker.model.Order
import com.portfolio.tracker.repository.AccountRepository
import com.portfolio.tracker.util.LoadingState
import com.portfolio.tracker.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

internal class AccountViewModel : ViewModel() {

    val loadingState = MutableLiveData<LoadingState>()
    val balanceData = MutableLiveData<List<Asset>>()
    val orderData = MutableLiveData<List<Order>>()
    val cancelOrderData = MutableLiveData<Order>()
    val createOrderData = MutableLiveData<Order>()
    private var repository = AccountRepository()

    fun fetchAccountData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loadingState.postValue(LoadingState.LOADING)
                val response = repository.getDailySnapshot()
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        balanceData.postValue(response.data!!)
                        loadingState.postValue(LoadingState.LOADED)
                    }
                    Resource.Status.ERROR -> loadingState.postValue(LoadingState.error(response.message))
                }
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