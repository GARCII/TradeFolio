package com.portfolio.tracker.model

import android.content.Context
import androidx.core.content.ContextCompat
import com.portfolio.tracker.R
import org.knowm.xchange.currency.Currency


fun Currency.getResourceId(context: Context) = when (this.currencyCode) {
    "ADA" -> R.drawable.ada
    "AION" -> R.drawable.aion
    "HOT" -> R.drawable.hot
    "VTHO" -> R.drawable.vtho
    "XRP" -> R.drawable.xrp
    "UNI" -> R.drawable.uni
    "WTC" -> R.drawable.wtc
    "MITH" -> R.drawable.mith
    "SOL" -> R.drawable.sol
    "BNB" -> R.drawable.bnb
    "UTK" -> R.drawable.utk
    "MATIC" -> R.drawable.matic
    "BAND" -> R.drawable.band
    "BTT" -> R.drawable.btt
    "ANKR" -> R.drawable.ankr
    "SUSHI" -> R.drawable.sushi
    "ALGO" -> R.drawable.algo
    "ONT" -> R.drawable.ont
    "THETA" -> R.drawable.theta
    "VET" -> R.drawable.vet
    "USDT" -> R.drawable.usdt
    "LSK" -> R.drawable.lsk
    "KSM" -> R.drawable.ksm
    "FIL" -> R.drawable.fil
    "AAVE" -> R.drawable.aave
    "ATOM" -> R.drawable.atom
    "XTZ" -> R.drawable.xtz
    "BCH" -> R.drawable.bch
    "BTC","XBT" -> R.drawable.btc
    "COMP" -> R.drawable.comp
    "DOGE" -> R.drawable.doge
    "ETC" -> R.drawable.etc
    "DOT" -> R.drawable.dot
    "TRX" -> R.drawable.trx
    "GRT" -> R.drawable.grt
    "OMI" -> R.drawable.omi
    "AR" -> R.drawable.ar
    "AUDIO" -> R.drawable.audio
    "CHZ" -> R.drawable.chz
    "RAY" -> R.drawable.ray
    "TLM" -> R.drawable.tlm
    "LUNA" -> R.drawable.luna
    "NBS" -> R.drawable.nbs
    "FTM" -> R.drawable.ftm
    "FTT" -> R.drawable.ftt
    "SHIB" -> R.drawable.shib
    "PNT" -> R.drawable.pnt
    "CAKE" -> R.drawable.cake
    "NU" -> R.drawable.nu
    "QI" -> R.drawable.qi
    "DYDX" -> R.drawable.dydx
    "SUPER" -> R.drawable.superr
    "STMX" -> R.drawable.stmx
    "AKRO" -> R.drawable.akro
    "COTI" -> R.drawable.coti
    "YGG" -> R.drawable.ygg
    "RUNE" -> R.drawable.rune
    "RSR" -> R.drawable.rsr
    "RLC" -> R.drawable.rlc
    "BMON" -> R.drawable.bmon
    "XTM" -> R.drawable.xtm
    "LOCG" -> R.drawable.locg
    "VRA" -> R.drawable.vra
    "PBX" -> R.drawable.pbx
    "HERO" -> R.drawable.hero
    "BLOK" -> R.drawable.block
    "KCS" -> R.drawable.kcs
    else -> R.drawable.default_crytpo
}.let {
    ContextCompat.getDrawable(context, it)
}