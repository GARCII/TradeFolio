package com.portfolio.tracker.model

enum class CurrencyCategory {
    DEFI, METAVERSE, GAMING, NFT, STABLECOIN,
}

enum class CurrencyType {
    FIAT, CRYPTO;

    fun isCrypto() = this == CRYPTO

    fun isFiat() = this == FIAT
}