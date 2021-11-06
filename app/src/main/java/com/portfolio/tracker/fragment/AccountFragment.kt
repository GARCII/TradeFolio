package com.portfolio.tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.portfolio.tracker.R
import com.portfolio.tracker.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_account.*


class AccountFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::viewModel.isInitialized) {
            viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            //viewModel.fetchAccountData()
            //viewModel.fetchOpenOrders()
            viewModel.cancelOrder("8177974279", "BTCUSDT")
            //viewModel.createOrder("BTCUSDT","BUY", "LIMIT", 0.001F, 52000.0F)
        }

        viewModel.orderData.observe(requireActivity(),  {
            it.forEach {
                Log.e("ORDER_TICKER", it.symbol + it.price)
            }
        })
        viewModel.balanceData.observe(requireActivity(), {
            it.forEach {
                Log.e("ASSET_TICKER", it.free)
            }
        })
        viewModel.cancelOrderData.observe(requireActivity(), {
            Log.e("cancelOrderStatus", it.status + it.symbol)
        })
        viewModel.createOrderData.observe(requireActivity(), {
            Log.e("created order", it.status + it.symbol + it.orderId)
        })
    }

    companion object {
        fun newInstance() =AccountFragment()
    }
}