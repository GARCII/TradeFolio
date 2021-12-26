package com.portfolio.tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.portfolio.tracker.R
import com.portfolio.tracker.activity.ExchangeListActivity
import com.portfolio.tracker.activity.HoldingListActivity
import com.portfolio.tracker.model.ExchangeType
import com.portfolio.tracker.util.LoadingState
import com.portfolio.tracker.viewmodel.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_connect_exchange.progress_circular
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.knowm.xchange.currency.Currency


class DashboardFragment : Fragment() {
    private lateinit var viewModel: ExchangeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_dashboard, container, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::viewModel.isInitialized) {
            viewModel = ViewModelProvider(this).get(ExchangeViewModel::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_synchronize.setOnClickListener {

            ExchangeType.sanitizeExchanges().forEach { exchange ->
                viewModel.connectPortfolio(requireContext(), exchange)
            }
        }
        button_list_exchange.setOnClickListener {
            activity?.let { activity ->
                ExchangeListActivity.launchActivity(activity)
            }
        }
        button_list_holding.setOnClickListener {
            activity?.let { activity ->
                HoldingListActivity.launchActivity(activity)
            }
        }

        viewModel.loadingState.observe(viewLifecycleOwner, {
            manageLoading(it)
        })
        viewModel.data.observe(requireActivity(), {
            it.entries.forEach { entry ->
                Log.e("Wallet", "${entry.value.getBalance(Currency.USDT).total}")
            }
        })
    }

    private fun manageLoading(loadingState: LoadingState) {
        when (loadingState.status) {
            LoadingState.Status.LOADING -> progress_circular.visibility = View.VISIBLE
            LoadingState.Status.SUCCESS -> progress_circular.visibility = View.INVISIBLE
            LoadingState.Status.ERROR -> {
                progress_circular.visibility = View.INVISIBLE
                loadingState.msg?.let {
                    Log.e("Wallet", it)
                }
            }
        }
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}