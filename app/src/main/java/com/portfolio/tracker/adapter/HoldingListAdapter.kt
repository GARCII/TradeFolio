package com.portfolio.tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.portfolio.tracker.R
import com.portfolio.tracker.model.BalanceData
import com.portfolio.tracker.model.getResourceId
import com.portfolio.tracker.util.getCoinMarketImageUrl
import com.portfolio.tracker.viewmodel.ExchangeViewModel

internal class HoldingListAdapter(
    private val context: Context,
    private val listener: HoldingListListener,
    private val viewModel: ExchangeViewModel
) : RecyclerView.Adapter<HoldingListAdapter.HoldingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): HoldingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tf_holding_list_item, parent, false)
        return HoldingViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        val balanceData = viewModel.getHoldings().toList()[position]
        holder.apply {
            bind(balanceData)
            itemView.setOnClickListener {
                listener.onHoldingClicked(balanceData)
            }
        }
    }

    override fun getItemCount(): Int = viewModel.getHoldings().size

    class HoldingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val context = itemView.context
        private val name = itemView.findViewById<TextView>(R.id.text_view_holding_name)
        private val imageView = itemView.findViewById<ImageView>(R.id.image_view_holding)

        fun bind(balanceData: BalanceData) {
            name.text = "${balanceData.currency.displayName} - ${balanceData.currency.currencyCode}"
            balanceData.id?.let {
                Glide.with(context).load(getCoinMarketImageUrl(it)).into(imageView);
            }
            //imageView.setImageDrawable(balanceData.currency.getResourceId(context))
        }
    }

    interface HoldingListListener {
        fun onHoldingClicked(balanceData: BalanceData)
    }
}