package com.portfolio.tracker.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.portfolio.tracker.R
import com.portfolio.tracker.model.ExchangeType
import com.portfolio.tracker.util.TradeFolioSharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_connect_exchange.*


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

        val sharedPreferencesUtils = TradeFolioSharedPreferencesUtils(this)
        val exchange = intent.getSerializableExtra(EXCHANGE_ID_EXTRA) as ExchangeType
        text_view_exchange_name.text = exchange.getName(this)
        image_view_exchange_icon.setImageDrawable(exchange.getResourceId(this))

        button_connect.setOnClickListener {
            val apiKey = edit_text_api_key.text.toString()
            val secretKey = edit_text_secret_key.text.toString()
            sharedPreferencesUtils.apply {
                setString(exchange.getApiPrefKey(), apiKey)
                setString(exchange.getSecretPrefKey(), secretKey)
            }
        }

        button_get_data.setOnClickListener {
            val apiKeyPref = sharedPreferencesUtils.getString(exchange.getApiPrefKey())
            val secretKeyPref = sharedPreferencesUtils.getString(exchange.getSecretPrefKey())
            Log.e("TEST", "$apiKeyPref $secretKeyPref")
        }
    }
}