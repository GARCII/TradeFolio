package com.portfolio.tracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.portfolio.tracker.R
import com.portfolio.tracker.adapter.HoldingsFragmentPagerAdapter
import com.portfolio.tracker.model.ExchangeType
import com.portfolio.tracker.viewmodel.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_holding_detail.*

class HoldingDetailFragment : Fragment() {

    private lateinit var viewModel: ExchangeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_holding_detail, container, false)


    companion object {
        @JvmStatic
        fun newInstance() = HoldingDetailFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()

    }

    private fun setViewPager() {
        val exchanges = ExchangeType.sanitizeExchanges()
        view_pager_holdings.apply {
            offscreenPageLimit = exchanges.size
            adapter = HoldingsFragmentPagerAdapter(
                requireContext(),
                childFragmentManager,
                exchanges
            )
        }
        tab_layout_holdings.setupWithViewPager(view_pager_holdings)
    }
}