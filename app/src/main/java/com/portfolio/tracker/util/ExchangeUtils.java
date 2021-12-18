package com.portfolio.tracker.util;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ascendex.AscendexExchange;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.ftx.FtxExchange;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.huobi.HuobiExchange;
import org.knowm.xchange.kucoin.KucoinExchange;


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
}
