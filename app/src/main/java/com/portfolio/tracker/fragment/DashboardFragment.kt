package com.portfolio.tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.portfolio.tracker.R
import com.portfolio.tracker.viewmodel.BinanceViewModel
import com.portfolio.tracker.viewmodel.FTXViewModel
import kotlinx.android.synthetic.main.fragment_account.*

import com.kucoin.sdk.KucoinRestClient

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.knowm.xchange.currency.Currency


class DashboardFragment : Fragment() {
    private lateinit var binanceViewModel: BinanceViewModel
    private lateinit var ftxViewModel: FTXViewModel
    private lateinit var kucoinRestClient: KucoinRestClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::binanceViewModel.isInitialized) {
             binanceViewModel = ViewModelProvider(this).get(BinanceViewModel::class.java)
         }
        // val kucoinPrivateWSClient = builder.buildPrivateWSClient()
        // val kucoinPublicWSClient = builder.buildPublicWSClient()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            binanceViewModel.fetchAccountData()
            //binanceViewModel.fetchOpenOrders()
            //binanceViewModel.cancelOrder("8177974279", "BTCUSDT")
            //binanceViewModel.createOrder("BTCUSDT","BUY", "LIMIT", 0.001F, 52000.0F)


            CoroutineScope(Dispatchers.IO).launch {

            }
        }

        binanceViewModel.balanceData.observe(requireActivity(), {
            it.keys.forEach { keyWallet ->
                Log.e("ASSET_TICKER","${it[keyWallet]?.getBalance(Currency.USDT)}")
            }
        })

        /*  .apply {

              balanceData.observe(requireActivity(), {
                  it.forEach {
                      Log.e("ASSET_TICKER", it.free)
                  }
              })
              cancelOrderData.observe(requireActivity(), {
                  Log.e("cancelOrderStatus", it.status + it.symbol)
              })
              createOrderData.observe(requireActivity(), {
                  Log.e("created order", it.status + it.symbol + it.orderId)
              })
          }*/
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}