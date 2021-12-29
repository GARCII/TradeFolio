package com.portfolio.tracker.core.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

@Entity(tableName = "Portfolio")
data class Portfolio(
    @PrimaryKey
    val portfolioId: String,
    val exchangeType: String,
    val lastUpdate: Date,
)

@Entity(tableName = "Bag")
data class Bag(
    @PrimaryKey
    val bagId: String,
    val portfolioId: String,
    val name: String,
    val type: String,
)

@Entity(tableName = "Holding")
data class Holding(
    @PrimaryKey
    val id: String,
    val bagId: String,
    val total: Double,
    val available: Double,
    val frozen: Double,
    val borrowed: Double,
    val loaned: Double,
    val withdrawing: Double,
    val depositing: Double,
    @Embedded(prefix = "Coin_")
    val coin: Coin,
)

@Entity(tableName = "CoinInfo")
data class CoinInfo(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val slug: String,
    val rank: Int,
    val circulationSupply: String,
    val totalSupply: String,
    val maxSupply: String,
    @Embedded(prefix = "CoinQuote_")
    val coinQuote: CoinQuote
)

data class PortfolioWithBags(
    @Embedded val portfolio: Portfolio,
    @Relation(
        parentColumn = "portfolioId",
        entityColumn = "portfolioId"
    )
    val bags: List<Bag>
)

data class BagWithHoldings(
    @Embedded val bag: Bag,
    @Relation(
        parentColumn = "bagId",
        entityColumn = "bagId"
    )
    val holdings: List<Holding>
)

data class Coin(
    val displayName: String,
    val currencyCode: String
)

data class CoinQuote(
    val price: String,
    val dayVolume: String,
    val marketCap: String,
    val hourChange: String,
    val dayChange: String,
    val weekChange: String
)
