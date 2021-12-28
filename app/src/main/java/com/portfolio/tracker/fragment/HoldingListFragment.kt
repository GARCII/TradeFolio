package com.portfolio.tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.portfolio.tracker.R
import com.portfolio.tracker.adapter.HoldingListAdapter
import com.portfolio.tracker.model.BalanceData
import com.portfolio.tracker.model.ExchangeType
import com.portfolio.tracker.model.ExchangeTypeItem
import com.portfolio.tracker.util.LoadingState
import com.portfolio.tracker.viewmodel.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_holding_list.*
import kotlinx.android.synthetic.main.fragment_holding_list.view.*

class HoldingListFragment : Fragment(), HoldingListAdapter.HoldingListListener {

    private var adapter: HoldingListAdapter? = null
    private lateinit var viewModel: ExchangeViewModel
    private lateinit var exchangeType: ExchangeTypeItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_holding_list, container, false)

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(ARG_EXCHANGE_TYPE, exchangeType)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            exchangeType = it.getSerializable(ARG_EXCHANGE_TYPE) as ExchangeTypeItem
        }

        if (!this::viewModel.isInitialized) {
            viewModel = ViewModelProviders.of(
                this,
                ExchangeViewModel.ExchangeViewModelFactory(exchangeType)
            ).get(ExchangeViewModel::class.java)
        }
        context?.let { context ->
            viewModel.synchronize(context)
            viewModel.isExchangeConnected.observe(requireActivity(), {
                if (it && viewModel.isDisplayable()) {
                    view.recycler_view_holding_list.layoutManager =
                        LinearLayoutManager(context)
                    adapter = HoldingListAdapter(context, this, viewModel)
                    view.recycler_view_holding_list.adapter = adapter
                } else {
                    view.recycler_view_holding_list.visibility = View.GONE
                    view.empty_balance_view.visibility = View.VISIBLE
                }
            })
            viewModel.loadingState.observe(viewLifecycleOwner, {
                manageLoading(it)
            })
            viewModel.tickers.observe(requireActivity(), {
                viewModel.geQuotes(it)
            })
        }
    }

    companion object {
        const val ARG_EXCHANGE_TYPE = "arg-exchange-type"

        @JvmStatic
        fun newInstance(exchangeType: ExchangeTypeItem) = HoldingListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_EXCHANGE_TYPE, exchangeType)
            }
        }
    }

    override fun onHoldingClicked(balanceData: BalanceData) {
        Log.e("TEST", balanceData.currency.displayName)
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
}