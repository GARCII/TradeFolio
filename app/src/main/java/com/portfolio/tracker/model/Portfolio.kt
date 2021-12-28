package com.portfolio.tracker.model

import org.knowm.xchange.currency.Currency
import org.knowm.xchange.dto.account.Wallet
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

data class Portfolio(
    val username: String?,
    val exchangeType: ExchangeTypeItem,
    val timestamp: Date,
    val wallets: List<WalletData>
) : Serializable

data class WalletData(
    val id: String?,
    val balances: List<BalanceData>,
    val walletName: String,
    val walletType: Set<Wallet.WalletFeature>
) : Serializable

data class BalanceData(
    val total: BigDecimal,
    val available: BigDecimal,
    val frozen: BigDecimal,
    val borrowed: BigDecimal,
    val loaned: BigDecimal,
    val withdrawing: BigDecimal,
    val depositing: BigDecimal,
    val updateData: Date,
    val currency: Currency,
    val id: String,
    val currentPrice: String
) : Serializable