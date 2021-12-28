package com.portfolio.tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.tracker.R
import com.portfolio.tracker.model.ExchangeType
import com.portfolio.tracker.model.ExchangeTypeItem

class ExchangeListAdapter(
    private val context: Context,
    private val listener: ExchangeListListener
) : RecyclerView.Adapter<ExchangeListAdapter.ExchangeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ExchangeViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.tf_exchange_list_item, parent, false)
        return ExchangeViewHolder(view)
    }

    override fun getItemCount() = ExchangeType.sanitizeExchanges().size

    override fun onBindViewHolder(parent: ExchangeViewHolder, position: Int) {
        val exchangeList = ExchangeType.sanitizeExchanges()
        val exchange = exchangeList[position]
        parent.apply {
            bind(exchange)
            itemView.setOnClickListener {
                listener.onExchangeClicked(exchange)
            }
        }
    }

    class ExchangeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val context = itemView.context
        private val name = itemView.findViewById<TextView>(R.id.text_view_exchange_name)
        private val imageView = itemView.findViewById<ImageView>(R.id.image_view_exchange)

        fun bind(exchangeType: ExchangeTypeItem) {
            name.text = exchangeType.getName(context)
            imageView.setImageDrawable(exchangeType.getImageResource(context))
        }
    }

    interface ExchangeListListener {
        fun onExchangeClicked(exchangeType: ExchangeTypeItem)
    }
}