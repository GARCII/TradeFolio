package com.portfolio.tracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.portfolio.tracker.repository.CoinMarketRepository
import com.portfolio.tracker.util.LoadingState
import com.portfolio.tracker.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.knowm.xchange.currency.Currency
import java.io.IOException

class CurrencyViewModel(private val currency: Currency) : ViewModel() {
    val loadingState = MutableLiveData<LoadingState>()

    private var repository: CoinMarketRepository = CoinMarketRepository()

    fun geQuote() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loadingState.postValue(LoadingState.LOADING)
                val response = repository.getQuote(currency.symbol)
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        loadingState.postValue(LoadingState.LOADED)
                    }
                    Resource.Status.ERROR -> loadingState.postValue(LoadingState.error(response.message))
                }
            } catch (e: IOException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class CurrencyViewModelFactory(private val Currency: Currency) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CurrencyViewModel(Currency) as T
        }
    }
}