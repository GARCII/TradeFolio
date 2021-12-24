package com.portfolio.tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.portfolio.tracker.R
import com.portfolio.tracker.activity.ConnectExchangeActivity
import com.portfolio.tracker.activity.ExchangeListActivity
import com.portfolio.tracker.model.ExchangeType
import com.portfolio.tracker.viewmodel.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_account.*


class DashboardFragment : Fragment() {
    private lateinit var exchangeViewModel: ExchangeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_account, container, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::exchangeViewModel.isInitialized) {
             exchangeViewModel = ViewModelProvider(this).get(ExchangeViewModel::class.java)
         }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_synchronize.setOnClickListener {
            exchangeViewModel.apply {
                fetchGateioData()
                fetchAscendexData()
                fetchBinanceData()
                fetchFtxData()
                fetchHuobiData()
                fetchKucoinData()
                fetchKrakenData()
                fetchBittrexData()
                fetchOkexData()
                //fetchCoinbaseData() // Disabled for 48H
                //fetchBitfinexData()
                fetchBitmexData()
                fetchDeribitData()
            }
        }

        button_list_exchange.setOnClickListener {
            activity?.let { activity ->
                ExchangeListActivity.launchActivity(activity)
            }
        }

        exchangeViewModel.balanceData.observe(requireActivity(), {
            it.keys.forEach { keyWallet ->
                Log.e("ASSET_TICKER","${it[keyWallet]?.name}")
            }
        })
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}