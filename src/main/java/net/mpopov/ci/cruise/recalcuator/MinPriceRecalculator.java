package net.mpopov.ci.cruise.recalcuator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;

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

    private boolean recalculated;

    private ListMultimap<Short, CurrencyExchangeRate> filteredProcessedCurrencyExchangeRates = ArrayListMultimap
            .create();

    public boolean recalculate()
    {
        log.info("MinPriceRecalculator started.");

        initialize();

        if (!filteredProcessedCurrencyExchangeRates.isEmpty())
        {
            recalculateMinPrices();
            recalculated = true;
        }

        log.info("MinPriceRecalculator finished.");

        return recalculated;
    }

    private void recalculateMinPrices()
    {
        CruiseDateRangeMinPriceInfoService cruiseDateRangeMinPriceInfoService = getBean(
                "cruiseDateRangeMinPriceInfoService");
        CruiseDateRangeMinPriceService cruiseDateRangeMinPriceService = getBean(
                "cruiseDateRangeMinPriceService");

        for (Short sourceType : filteredProcessedCurrencyExchangeRates.keySet())
        {
            cruiseDateRangeMinPriceInfoService.remove(sourceType);

            List<CruiseDateRangeMinPrice> cruiseDateRangeMinPrices = cruiseDateRangeMinPriceService
                    .list(sourceType);

            for (CruiseDateRangeMinPrice cruiseDateRangeMinPrice : cruiseDateRangeMinPrices)
            {
                Long currencyId = cruiseDateRangeMinPrice.getCurrency()
                        .getCurrencyId();
                Double minPriceValue = cruiseDateRangeMinPrice
                        .getMinPriceValue();
                List<CurrencyExchangeRate> currencyExchangeRates = filteredProcessedCurrencyExchangeRates
                        .get(sourceType);

                Map<Long, Double> currencyToRecalculatedPriceValues = CurrencyUtil
                        .recalculatePriceValues(currencyId, minPriceValue,
                                currencyExchangeRates);

                List<CruiseDateRangeMinPriceInfo> cruiseDateRangeMinPriceInfos = Lists
                        .newArrayList();
                for (Entry<Long, Double> currencyToRecalculatedPriceValue : currencyToRecalculatedPriceValues
                        .entrySet())
                {
                    CruiseDateRangeMinPriceInfo cruiseDateRangeMinPriceInfo = new CruiseDateRangeMinPriceInfo();
                    cruiseDateRangeMinPriceInfo.setCruiseDateRange(
                            cruiseDateRangeMinPrice.getCruiseDateRange());
                    cruiseDateRangeMinPriceInfo.setCruiseDateRangeMinPrice(
                            cruiseDateRangeMinPrice);
                    cruiseDateRangeMinPriceInfo.setCurrencyId(
                            currencyToRecalculatedPriceValue.getKey());
                    cruiseDateRangeMinPriceInfo.setPriceValue(
                            currencyToRecalculatedPriceValue.getValue());

                    cruiseDateRangeMinPriceInfos
                            .add(cruiseDateRangeMinPriceInfo);
                }

                cruiseDateRangeMinPriceInfoService
                        .add(cruiseDateRangeMinPriceInfos);
            }
        }
    }

    private void initialize()
    {
        CurrencyExchangeRateService currencyExchangeRateService = getBean(
                "currencyExchangeRateService");
        List<CurrencyExchangeRate> currencyExchangeRates = currencyExchangeRateService
                .list();

        CurrencyExchangeRateLogService currencyExchangeRateLogService = getBean(
                "currencyExchangeRateLogService");
        List<CurrencyExchangeRateLog> currencyExchangeLogRates = currencyExchangeRateLogService
                .list();

        for (CurrencyExchangeRate currencyExchangeRate : currencyExchangeRates)
        {
            boolean isExists = false;
            for (CurrencyExchangeRateLog currencyExchangeRateLog : currencyExchangeLogRates)
            {
                boolean isEqual = currencyExchangeRateLog
                        .getSourceType() == currencyExchangeRate
                                .getSourceType();
                isEqual &= currencyExchangeRateLog
                        .getCurrencyId() == currencyExchangeRate
                                .getCurrencyId();
                isEqual &= currencyExchangeRateLog
                        .getForCurrencyId() == currencyExchangeRate
                                .getForCurrencyId();
                isEqual &= currencyExchangeRateLog
                        .getForCurrencyId() == currencyExchangeRate
                                .getForCurrencyId();
                isEqual &= currencyExchangeRateLog
                        .getDateTime() == currencyExchangeRate.getDateTime();
                isEqual &= currencyExchangeRateLog
                        .getRateValue() == currencyExchangeRate.getRateValue();
                if (isEqual)
                {
                    isExists = true;
                    break;
                }
            }

            if (!isExists)
            {
                filteredProcessedCurrencyExchangeRates.put(
                        currencyExchangeRate.getSourceType(),
                        currencyExchangeRate);
            }
        }
    }

}
