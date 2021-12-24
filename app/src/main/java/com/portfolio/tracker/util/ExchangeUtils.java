package com.portfolio.tracker.util;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ascendex.AscendexExchange;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.bitfinex.BitfinexExchange;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.ftx.FtxExchange;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.huobi.HuobiExchange;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.okex.v5.OkexExchange;


public class ExchangeUtils {

    public static Exchange getKucoinExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(KucoinExchange.class);
        spec.setApiKey(ConstantsKt.KUCOIN_API_KEY);
        spec.setSecretKey(ConstantsKt.KUCOIN_API_SECRET);
        spec.setExchangeSpecificParametersItem("passphrase", "Garousc0L!");
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getBinanceExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(BinanceExchange.class);
        spec.setApiKey(ConstantsKt.API_KEY);
        spec.setSecretKey(ConstantsKt.API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getAscendexExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(AscendexExchange.class);
        spec.setApiKey(ConstantsKt.ASCENDEX_API_KEY);
        spec.setSecretKey(ConstantsKt.ASCENDEX_API_SECRET);
        spec.setExchangeSpecificParametersItem("account-group", 4);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getFtxExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(FtxExchange.class);
        spec.setApiKey(ConstantsKt.FTX_API_KEY);
        spec.setSecretKey(ConstantsKt.FTX_API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    //TODO Fix gate.io connection (https://www.gate.io/docs/developers/apiv4/en/#gate-api-v4-v4-23-0)
    public static Exchange getGateioExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(GateioExchange.class);
        spec.setApiKey(ConstantsKt.GATE_API_KEY);
        spec.setSecretKey(ConstantsKt.GATE_API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getHuobiExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(HuobiExchange.class);
        spec.setApiKey(ConstantsKt.HUOBI_API_KEY);
        spec.setSecretKey(ConstantsKt.HUOBI_API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getKrakenExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(KrakenExchange.class);
        spec.setApiKey(ConstantsKt.KRAKEN_API_KEY);
        spec.setSecretKey(ConstantsKt.KRAKEN_API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getBittrexExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(BittrexExchange.class);
        spec.setApiKey(ConstantsKt.BITTREX_API_KEY);
        spec.setSecretKey(ConstantsKt.BITTREX_API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getOkexExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(OkexExchange.class);
        spec.setApiKey(ConstantsKt.OKEX_API_KEY);
        spec.setSecretKey(ConstantsKt.OKEX_API_SECRET);
        spec.setExchangeSpecificParametersItem("passphrase", "301994");
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getCoinbaseExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(CoinbaseExchange.class);
        spec.setApiKey(ConstantsKt.COINBASE_API_KEY);
        spec.setSecretKey(ConstantsKt.COINBASE_API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    //TODO Runtime exception "Ticker not valid"
    public static Exchange getBitfinexExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(BitfinexExchange.class);
        spec.setApiKey(ConstantsKt.BITFINEX_API_KEY);
        spec.setSecretKey(ConstantsKt.BITFINEX_API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getBitmexExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(BitmexExchange.class);
        spec.setApiKey(ConstantsKt.BITMEX_API_KEY);
        spec.setSecretKey(ConstantsKt.BITMEX_API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static Exchange getDeribitExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(DeribitExchange.class);
        spec.setApiKey(ConstantsKt.DERIBIT_API_KEY);
        spec.setSecretKey(ConstantsKt.DERIBIT_API_SECRET);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }
}
