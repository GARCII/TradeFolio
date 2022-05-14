package com.portfolio.tracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tracker.model.CoinMarket
import com.portfolio.tracker.repository.CoinMarketRepository
import com.portfolio.tracker.util.LoadingState
import com.portfolio.tracker.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class CurrencyViewModel : ViewModel() {

    val loadingState = MutableLiveData<LoadingState>()

}