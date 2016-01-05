package net.mpopov.ci.cruise.recalcuator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.Set;

import net.mpopov.ci.common.ContextAdapter;
import net.mpopov.ci.common.CurrencyUtil;
import net.mpopov.ci.cruise.model.CruiseCabinPrice;
import net.mpopov.ci.cruise.model.CruisePriceInfo;
import net.mpopov.ci.cruise.model.CurrencyExchangeRate;
import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;
import net.mpopov.ci.cruise.service.CruiseCabinPriceService;
import net.mpopov.ci.cruise.service.CruisePriceInfoService;
import net.mpopov.ci.cruise.service.CurrencyExchangeRateLogService;
import net.mpopov.ci.cruise.service.CurrencyExchangeRateService;

public class PriceInfoRecalculator extends ContextAdapter implements Recalculator
{
    private static Logger log = Logger.getLogger(PriceInfoRecalculator.class);

    private Set<Short> needToRecalculate = new HashSet<Short>();
    
    private List<CurrencyExchangeRate> currencyExchangeRates;

    public boolean recalculate()
    {
        log.info("PriceInfoRecalculator started");
        boolean recalculated = false;
        init();

        if (!needToRecalculate.isEmpty())
        {
            recalculatePricesInfo();
            recalculated = true;
        }
        log.info("PriceInfoRecalculator finished");
        return recalculated;
    }

    private void recalculatePricesInfo()
    {
        CruisePriceInfoService cruisePriceInfoService = getBean(
                "cruisePriceInfoService");
        CruiseCabinPriceService cruiseCabinPriceService = getBean(
                "cruiseCabinPriceService");

        Map<Short, Set<CurrencyExchangeRate>> ratesForSourceType = new HashMap<Short, Set<CurrencyExchangeRate>>();
        for (CurrencyExchangeRate currencyExchangeRate : currencyExchangeRates)
        {
            Short sourceType = currencyExchangeRate.getSourceType();
            if (needToRecalculate.contains(sourceType))
            {
                if (!ratesForSourceType.containsKey(sourceType))
                {
                    ratesForSourceType.put(sourceType,
                            new HashSet<CurrencyExchangeRate>());
                }
                ratesForSourceType.get(sourceType).add(currencyExchangeRate);
            }
        }
        
        for (Entry<Short, Set<CurrencyExchangeRate>> entry : ratesForSourceType.entrySet())
        {
            cruisePriceInfoService.remove(entry.getKey());
            List<CruiseCabinPrice> cabinPrices = cruiseCabinPriceService
                    .list(entry.getKey());
            for (CruiseCabinPrice cabinPrice : cabinPrices)
            {
                Map<Long, Double> prices = CurrencyUtil.getPricesByCurrencies(
                        cabinPrice.getCruisePrice().getCurrency().getCurrencyId(),
                        cabinPrice.getPriceValue().doubleValue(),
                        entry.getValue());
                Map<Long, Double> pricesPortAdult = CurrencyUtil.getPricesByCurrencies(
                        cabinPrice.getCruisePrice().getCurrency().getCurrencyId(),
                        cabinPrice.getPricePortAdult() == null ? 0.0 : cabinPrice.getPricePortAdult().doubleValue(),
                        entry.getValue());
                Map<Long, Double> pricesPortChild = CurrencyUtil.getPricesByCurrencies(
                        cabinPrice.getCruisePrice().getCurrency().getCurrencyId(),
                        cabinPrice.getPricePortChild() == null ? 0.0 :cabinPrice.getPricePortChild().doubleValue(),
                        entry.getValue());
                Map<Long, Double> pricesValueOne = CurrencyUtil
                        .getPricesByCurrencies(
                                cabinPrice.getCruisePrice().getCurrency()
                                        .getCurrencyId(),
                                cabinPrice.getPriceValueOne() == null ? 0.0
                                        : cabinPrice.getPriceValueOne()
                                                .doubleValue(),
                                entry.getValue());
                Map<Long, Double> pricesValueTwo = CurrencyUtil
                        .getPricesByCurrencies(
                                cabinPrice.getCruisePrice().getCurrency()
                                        .getCurrencyId(),
                                cabinPrice.getPriceValueTwo() == null ? 0.0
                                        : cabinPrice.getPriceValueTwo()
                                                .doubleValue(),
                                entry.getValue());
                Map<Long, Double> pricesValueThree = CurrencyUtil
                        .getPricesByCurrencies(
                                cabinPrice.getCruisePrice().getCurrency()
                                        .getCurrencyId(),
                                cabinPrice.getPriceValueThree() == null ? 0.0
                                        : cabinPrice.getPriceValueThree()
                                                .doubleValue(),
                                entry.getValue());
                Map<Long, Double> pricesValueFour = CurrencyUtil
                        .getPricesByCurrencies(
                                cabinPrice.getCruisePrice().getCurrency()
                                        .getCurrencyId(),
                                cabinPrice.getPriceValueFour() == null ? 0.0
                                        : cabinPrice.getPriceValueFour()
                                                .doubleValue(),
                                entry.getValue());
                Map<Long, Double> pricesValueAddChild = CurrencyUtil
                        .getPricesByCurrencies(
                                cabinPrice.getCruisePrice().getCurrency()
                                        .getCurrencyId(),
                                cabinPrice.getPriceValueAddChild() == null ? 0.0
                                        : cabinPrice.getPriceValueAddChild()
                                                .doubleValue(),
                                entry.getValue());
                Map<Long, Double> pricesValueAdd2Child = CurrencyUtil
                        .getPricesByCurrencies(
                                cabinPrice.getCruisePrice().getCurrency()
                                        .getCurrencyId(),
                                cabinPrice.getPriceValueAdd2Child() == null ? 0.0
                                        : cabinPrice.getPriceValueAdd2Child()
                                                .doubleValue(),
                                entry.getValue());

                for (Entry<Long, Double> price : prices.entrySet())
                {
                    CruisePriceInfo cruisePriceInfo = new CruisePriceInfo();
                    cruisePriceInfo.setCruiseDateRange(cabinPrice.getCruisePrice().getCruiseDateRange());
                    cruisePriceInfo.setCabinId(cabinPrice.getCabinId());
                    cruisePriceInfo.setCruisePriceId(cabinPrice.getCruisePrice().getCruisePriceId());
                    cruisePriceInfo.setCurrencyId(price.getKey());
                    cruisePriceInfo.setDateTime(cabinPrice.getCruisePrice().getDateTime());
                    cruisePriceInfo.setPricePortAdult(pricesPortAdult.get(price.getKey()));
                    cruisePriceInfo.setPricePortChild(pricesPortChild.get(price.getKey()));
                    cruisePriceInfo.setPriceValue(prices.get(price.getKey()));
                    cruisePriceInfo.setPriceValueAdd2Child(pricesValueAdd2Child.get(price.getKey()));
                    cruisePriceInfo.setPriceValueAddChild(pricesValueAddChild.get(price.getKey()));
                    cruisePriceInfo.setPriceValueOne(pricesValueOne.get(price.getKey()));
                    cruisePriceInfo.setPriceValueTwo(pricesValueTwo.get(price.getKey()));
                    cruisePriceInfo.setPriceValueThree(pricesValueThree.get(price.getKey()));
                    cruisePriceInfo.setPriceValueFour(pricesValueFour.get(price.getKey()));
                    cruisePriceInfoService.add(cruisePriceInfo);

                }
            }
        }
        
    }

    private void init()
    {
        CurrencyExchangeRateService currencyExchangeRateService = getBean(
                "currencyExchangeRateService");
        currencyExchangeRates = currencyExchangeRateService
                .list();

        CurrencyExchangeRateLogService currencyExchangeRateLogService = getBean(
                "currencyExchangeRateLogService");
        List<CurrencyExchangeRateLog> currencyExchangeLogRates = currencyExchangeRateLogService
                .list();
        
        for (CurrencyExchangeRateLog currencyExchangeRateLog : currencyExchangeLogRates)
        {
            CurrencyExchangeRate currCurrencyExchangeRate = new CurrencyExchangeRate();
            currCurrencyExchangeRate
                    .setCurrencyId(currencyExchangeRateLog.getCurrencyId());
            currCurrencyExchangeRate.setForCurrencyId(
                    currencyExchangeRateLog.getForCurrencyId());
            currCurrencyExchangeRate
                    .setSourceType(currencyExchangeRateLog.getSourceType());
            for (CurrencyExchangeRate currencyExchangeRate : currencyExchangeRates)
            {
                CurrencyExchangeRateLog currCurrencyExchangeRateLog = new CurrencyExchangeRateLog();
                currCurrencyExchangeRateLog
                        .setCurrencyId(currencyExchangeRate.getCurrencyId());
                currCurrencyExchangeRateLog.setForCurrencyId(
                        currencyExchangeRate.getForCurrencyId());
                currCurrencyExchangeRateLog
                        .setSourceType(currencyExchangeRate.getSourceType());
                if ((currCurrencyExchangeRateLog.equals(currencyExchangeRateLog)
                        && !currencyExchangeRate.getDateTime()
                                .equals(currencyExchangeRateLog.getDateTime()))
                        || !currencyExchangeLogRates
                                .contains(currCurrencyExchangeRateLog)
                        || !currencyExchangeRates
                                .contains(currCurrencyExchangeRate))
                {
                    needToRecalculate.add(currencyExchangeRate.getSourceType());
                }
            }
        }
    }

}
