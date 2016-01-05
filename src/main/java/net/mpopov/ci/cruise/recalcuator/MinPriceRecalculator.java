package net.mpopov.ci.cruise.recalcuator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import net.mpopov.ci.common.ContextAdapter;
import net.mpopov.ci.common.CurrencyUtil;
import net.mpopov.ci.cruise.model.CruiseDateRangeMinPrice;
import net.mpopov.ci.cruise.model.CruiseDateRangeMinPriceInfo;
import net.mpopov.ci.cruise.model.CurrencyExchangeRate;
import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;
import net.mpopov.ci.cruise.service.CruiseDateRangeMinPriceInfoService;
import net.mpopov.ci.cruise.service.CruiseDateRangeMinPriceService;
import net.mpopov.ci.cruise.service.CurrencyExchangeRateLogService;
import net.mpopov.ci.cruise.service.CurrencyExchangeRateService;

public class MinPriceRecalculator extends ContextAdapter implements Recalculator
{
    private static Logger log = Logger.getLogger(MinPriceRecalculator.class);

    private Set<Short> needToRecalculate = new HashSet<Short>();
    
    private List<CurrencyExchangeRate> currencyExchangeRates;

    public boolean recalculate()
    {
        log.info("MinPriceRecalculator started");
        boolean recalculated = false;
        init();

        if (!needToRecalculate.isEmpty())
        {
            recalculateMinPrices();
            recalculated = true;
        }
        log.info("MinPriceRecalculator finished");
        return recalculated;
    }

    private void recalculateMinPrices()
    {
        CruiseDateRangeMinPriceInfoService cruiseDateRangeMinPriceInfoService = getBean(
                "cruiseDateRangeMinPriceInfoService");
        CruiseDateRangeMinPriceService cruiseDateRangeMinPriceService = getBean(
                "cruiseDateRangeMinPriceService");

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
            cruiseDateRangeMinPriceInfoService.remove(entry.getKey());
            List<CruiseDateRangeMinPrice> minPrices = cruiseDateRangeMinPriceService
                    .list(entry.getKey());
            for (CruiseDateRangeMinPrice cruiseDateRangeMinPrice : minPrices)
            {
                Map<Long, Double> prices = CurrencyUtil.getPricesByCurrencies(
                        cruiseDateRangeMinPrice.getCurrency().getCurrencyId(),
                        cruiseDateRangeMinPrice.getMinPriceValue(),
                        entry.getValue());
                for (Entry<Long, Double> price : prices.entrySet())
                {
                    CruiseDateRangeMinPriceInfo cruiseDateRangeMinPriceInfo = new CruiseDateRangeMinPriceInfo();
                    cruiseDateRangeMinPriceInfo.setCruiseDateRange(cruiseDateRangeMinPrice.getCruiseDateRange());
                    cruiseDateRangeMinPriceInfo.setCruiseDateRangeMinPrice(cruiseDateRangeMinPrice);
                    cruiseDateRangeMinPriceInfo.setCurrencyId(price.getKey());
                    cruiseDateRangeMinPriceInfo.setPriceValue(price.getValue());
                    cruiseDateRangeMinPriceInfoService.add(cruiseDateRangeMinPriceInfo);
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
