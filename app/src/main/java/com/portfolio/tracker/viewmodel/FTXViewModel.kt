package com.portfolio.tracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tracker.remote.FXTAccountResponse
import com.portfolio.tracker.util.LoadingState
import com.portfolio.tracker.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

internal class FTXViewModel : ViewModel() {

    val loadingState = MutableLiveData<LoadingState>()
    val accountData = MutableLiveData<FXTAccountResponse>()
    private var repository = FTXRepository()

    fun fetchAccountData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loadingState.postValue(LoadingState.LOADING)
                val response = repository.getDailySnapshot()
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        accountData.postValue(response.data!!)
                        loadingState.postValue(LoadingState.LOADED)
                    }
                    Resource.Status.ERROR -> loadingState.postValue(LoadingState.error(response.message))
                }
            } catch (e: IOException) {
                loadingState.postValue(LoadingState.error("error_message"))
            }
        }
    }
}