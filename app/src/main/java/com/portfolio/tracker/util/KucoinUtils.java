package com.portfolio.tracker.util;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kucoin.KucoinExchange;


public class KucoinUtils {

  public static Exchange getExchange() {
    ExchangeSpecification spec = new ExchangeSpecification(KucoinExchange.class);
    spec.setApiKey(ConstantsKt.KUCOIN_API_KEY);
    spec.setSecretKey(ConstantsKt.KUCOIN_API_SECRET);
    spec.setExchangeSpecificParametersItem("passphrase", "Garousc0L!");
    return ExchangeFactory.INSTANCE.createExchange(spec);
  }
}
