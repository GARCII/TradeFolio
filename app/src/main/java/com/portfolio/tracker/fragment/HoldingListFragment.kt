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
import com.portfolio.tracker.util.LoadingState
import com.portfolio.tracker.viewmodel.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_holding_list.*

class HoldingListFragment : Fragment(), HoldingListAdapter.HoldingListListener {

    private var adapter: HoldingListAdapter? = null
    private lateinit var viewModel: ExchangeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_holding_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!this::viewModel.isInitialized) {
            viewModel = ViewModelProviders.of(this).get(ExchangeViewModel::class.java)
        }
        context?.let { context ->
            viewModel.apply {
                connectPortfolio(context, ExchangeType.ASCENDEX)
                isExchangeConned.observe(requireActivity(), {
                    if (it) {
                        recycler_view_holding_list.layoutManager = LinearLayoutManager(context)
                        adapter = HoldingListAdapter(context, this@HoldingListFragment, viewModel)
                        recycler_view_holding_list.adapter = adapter
                    }
                })
                loadingState.observe(viewLifecycleOwner, {
                    manageLoading(it)
                })
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoldingListFragment()
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