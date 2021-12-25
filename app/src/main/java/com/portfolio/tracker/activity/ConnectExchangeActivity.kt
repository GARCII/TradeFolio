package com.portfolio.tracker.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.portfolio.tracker.R
import com.portfolio.tracker.fragment.ConnectExchangeFragment
import com.portfolio.tracker.model.ExchangeType


class ConnectExchangeActivity : AppCompatActivity() {

    companion object {
        private const val EXCHANGE_ID_EXTRA = "exchange-id-extra"
        const val CONNECT_EXCHANGE_REQUEST_CODE = 100

        fun launchActivity(
            activity: Activity,
            exchange: ExchangeType
        ) {
            val intent = Intent(activity, ConnectExchangeActivity::class.java)
            intent.putExtra(EXCHANGE_ID_EXTRA, exchange)
            activity.startActivityForResult(intent, CONNECT_EXCHANGE_REQUEST_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_exchange)

        val exchange = intent.getSerializableExtra(EXCHANGE_ID_EXTRA) as ExchangeType

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ConnectExchangeFragment.newInstance(exchange))
            .commit()
    }
}