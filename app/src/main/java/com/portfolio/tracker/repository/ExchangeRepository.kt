package com.portfolio.tracker.repository

import android.content.Context
import com.portfolio.tracker.core.Core
import com.portfolio.tracker.core.entity.Coin
import com.portfolio.tracker.core.entity.Holding
import com.portfolio.tracker.model.ExchangeTypeItem
import com.portfolio.tracker.util.ConnectUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.knowm.xchange.ascendex.AscendexException
import org.knowm.xchange.binance.dto.BinanceException
import org.knowm.xchange.bitmex.BitmexException
import org.knowm.xchange.bittrex.dto.BittrexException
import org.knowm.xchange.coinbase.dto.CoinbaseException
import org.knowm.xchange.currency.Currency
import org.knowm.xchange.deribit.v2.dto.DeribitException
import org.knowm.xchange.exceptions.ExchangeException
import org.knowm.xchange.ftx.FtxException
import org.knowm.xchange.kucoin.service.KucoinApiException
import org.knowm.xchange.okex.v5.dto.OkexException
import java.io.IOException
import java.math.BigDecimal


class ExchangeRepository {

    val repository = Core.database.holdingDao()

    fun fetchExchange(context: Context, exchangeType: ExchangeTypeItem) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exchange = ConnectUtils.getExchange(context, exchangeType)
                exchange?.let {
                    val set = mutableSetOf<Holding>()
                    exchange.accountService.accountInfo.wallets.entries.forEach {
                        it.value.balances.forEach { balance ->
                            balance.value.apply {
                                if (this.total > BigDecimal(0.0) && balance.value.currency != Currency.USD) {
                                    set.add(
                                        Holding(
                                            this.currency.currencyCode,
                                            it.value.id,
                                            this.total.toDouble(),
                                            this.available.toDouble(),
                                            this.frozen.toDouble(),
                                            this.borrowed.toDouble(),
                                            this.loaned.toDouble(),
                                            this.withdrawing.toDouble(),
                                            this.depositing.toDouble(),
                                            Coin(
                                                this.currency.displayName,
                                                this.currency.currencyCode
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }
                    repository.insert(set.toList())
                } ?: run {

                }

            } catch (e: AscendexException) {

            } catch (e: BinanceException) {

            } catch (e: BitmexException) {

            } catch (e: BittrexException) {

            } catch (e: CoinbaseException) {

            } catch (e: DeribitException) {

            } catch (e: FtxException) {

            } catch (e: KucoinApiException) {

            } catch (e: OkexException) {

            } catch (e: ExchangeException) {

            } catch (e: IOException) {

            }
        }
    }


}