package com.portfolio.tracker.model

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.portfolio.tracker.R
import com.portfolio.tracker.fragment.HoldingListFragment
import org.knowm.xchange.BaseExchange

class AllHoldingType : ExchangeTypeItem {
    override fun getName(context: Context) = context.getString(R.string.tf_exchange_all_holdings)
    override fun getImageResource(context: Context) =
        ContextCompat.getDrawable(context, R.drawable.wallet)

    override fun getFragment(): Fragment = HoldingListFragment.newInstance(this)
    override fun isSyncAuthorized() = false
    override fun getSpecificParamItem(): SpecificExchangeParamType? = null
    override fun getExchangeReference(): Class<out BaseExchange>? = null
    override fun getSecretPrefKey(): String? = null
    override fun getApiPrefKey(): String? = null
}