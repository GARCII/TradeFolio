package com.portfolio.tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.portfolio.tracker.R
import com.portfolio.tracker.viewmodel.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_account.*

import org.knowm.xchange.currency.Currency


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
        button.setOnClickListener {
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
                fetchCoinbaseData()
            }
        }

        exchangeViewModel.balanceData.observe(requireActivity(), {
            it.keys.forEach { keyWallet ->
                Log.e("ASSET_TICKER","${it[keyWallet]?.getBalance(Currency.USDT)}")
            }
        })
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}