package com.portfolio.tracker.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.portfolio.tracker.R
import com.portfolio.tracker.adapter.ExchangeListAdapter
import com.portfolio.tracker.model.ExchangeType
import kotlinx.android.synthetic.main.activity_exchange_list.*

class ExchangeListActivity : AppCompatActivity(), ExchangeListAdapter.ExchangeListListener {

    private var adapter: ExchangeListAdapter? = null

    companion object {
        fun launchActivity(
            activity: Activity) {
            val intent = Intent(activity, ExchangeListActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_list)
        recycler_view_exchange_list.layoutManager = LinearLayoutManager(this)
        adapter = ExchangeListAdapter(this, this)
        recycler_view_exchange_list.adapter = adapter
    }

    override fun onExchangeClicked(exchangeType: ExchangeType) {
        ConnectExchangeActivity.launchActivity(this, exchangeType)
    }
}