package net.mpopov.ci.cruise.recalcuator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;

import net.mpopov.ci.common.ContextAdapter;
import net.mpopov.ci.common.CurrencyUtil;
import net.mpopov.ci.cruise.model.CruiseCabinPrice;
import net.mpopov.ci.cruise.model.CruiseDateRange;
import net.mpopov.ci.cruise.model.CruisePriceInfo;
import net.mpopov.ci.cruise.model.CurrencyExchangeRate;
import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;
import net.mpopov.ci.cruise.service.CruiseCabinPriceService;
import net.mpopov.ci.cruise.service.CruisePriceInfoService;
import net.mpopov.ci.cruise.service.CurrencyExchangeRateLogService;
import net.mpopov.ci.cruise.service.CurrencyExchangeRateService;

public class PriceInfoRecalculator extends ContextAdapter
        implements Recalculator
{
    private static Logger log = Logger.getLogger(PriceInfoRecalculator.class);

    private ListMultimap<Short, CurrencyExchangeRate> filteredProcessedCurrencyExchangeRates = ArrayListMultimap
            .create();

    public boolean recalculate()
    {
        log.info("PriceInfoRecalculator started");
        boolean recalculated = false;

        initialize();

        if (!filteredProcessedCurrencyExchangeRates.isEmpty())
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
        for (Short sourceType : filteredProcessedCurrencyExchangeRates.keySet())
        {
            cruisePriceInfoService.remove(sourceType);

            List<CruiseCabinPrice> cruiseCabinPrices = cruiseCabinPriceService
                    .list(sourceType);

            for (CruiseCabinPrice cruiseCabinPrice : cruiseCabinPrices)
            {
                List<CurrencyExchangeRate> currencyExchangeRates = filteredProcessedCurrencyExchangeRates
                        .get(sourceType);

                Long currencyId = cruiseCabinPrice.getCruisePrice()
                        .getCurrency().getCurrencyId();

                Map<Long, Double> priceValues = CurrencyUtil
                        .recalculatePriceValues(currencyId,
                                getPriceValue(cruiseCabinPrice.getPriceValue()),
                                currencyExchangeRates);

                Map<Long, Double> pricesPortAdult = CurrencyUtil
                        .recalculatePriceValues(currencyId,
                                getPriceValue(
                                        cruiseCabinPrice.getPricePortAdult()),
                                currencyExchangeRates);

                Map<Long, Double> pricesPortChild = CurrencyUtil
                        .recalculatePriceValues(currencyId,
                                getPriceValue(
                                        cruiseCabinPrice.getPricePortChild()),
                                currencyExchangeRates);

                Map<Long, Double> pricesValueOne = CurrencyUtil
                        .recalculatePriceValues(currencyId,
                                getPriceValue(
                                        cruiseCabinPrice.getPriceValueOne()),
                                currencyExchangeRates);

                Map<Long, Double> pricesValueTwo = CurrencyUtil
                        .recalculatePriceValues(currencyId,
                                getPriceValue(
                                        cruiseCabinPrice.getPriceValueTwo()),
                                currencyExchangeRates);

                Map<Long, Double> pricesValueThree = CurrencyUtil
                        .recalculatePriceValues(currencyId,
                                getPriceValue(
                                        cruiseCabinPrice.getPriceValueThree()),
                                currencyExchangeRates);

                Map<Long, Double> pricesValueFour = CurrencyUtil
                        .recalculatePriceValues(currencyId,
                                getPriceValue(
                                        cruiseCabinPrice.getPriceValueFour()),
                                currencyExchangeRates);

                Map<Long, Double> pricesValueAddChild = CurrencyUtil
                        .recalculatePriceValues(currencyId,
                                getPriceValue(cruiseCabinPrice
                                        .getPriceValueAddChild()),
                                currencyExchangeRates);

                Map<Long, Double> pricesValueAdd2Child = CurrencyUtil
                        .recalculatePriceValues(currencyId,
                                getPriceValue(cruiseCabinPrice
                                        .getPriceValueAdd2Child()),
                                currencyExchangeRates);

                List<CruisePriceInfo> cruisePriceInfos = Lists.newArrayList();
                for (Entry<Long, Double> priceValue : priceValues.entrySet())
                {
                    CruiseDateRange cruiseDateRange = cruiseCabinPrice
                            .getCruisePrice().getCruiseDateRange();
                    Long cabinId = cruiseCabinPrice.getCabinId();
                    Long cruisePriceId = cruiseCabinPrice.getCruisePrice()
                            .getCruisePriceId();
                    Date dateTime = cruiseCabinPrice.getCruisePrice()
                            .getDateTime();
                    Long priceVaueCurrency = priceValue.getKey();

                    CruisePriceInfo cruisePriceInfo = new CruisePriceInfo();
                    cruisePriceInfo.setCruiseDateRange(cruiseDateRange);
                    cruisePriceInfo.setCabinId(cabinId);
                    cruisePriceInfo.setCruisePriceId(cruisePriceId);
                    cruisePriceInfo.setCurrencyId(priceVaueCurrency);
                    cruisePriceInfo.setDateTime(dateTime);

                    Double currentPriceValue = priceValues
                            .get(priceVaueCurrency);
                    Double pricePortAdult = pricesPortAdult
                            .get(priceVaueCurrency);
                    Double pricePortChild = pricesPortChild
                            .get(priceVaueCurrency);
                    Double priceValueAdd2Child = pricesValueAdd2Child
                            .get(priceVaueCurrency);
                    Double priceValueAddChild = pricesValueAddChild
                            .get(priceVaueCurrency);
                    Double priceValueOne = pricesValueOne
                            .get(priceVaueCurrency);
                    Double priceValueTwo = pricesValueTwo
                            .get(priceVaueCurrency);
                    Double priceValueThree = pricesValueThree
                            .get(priceVaueCurrency);
                    Double priceValueFour = pricesValueFour
                            .get(priceVaueCurrency);

                    cruisePriceInfo.setPriceValue(currentPriceValue);
                    cruisePriceInfo.setPricePortAdult(pricePortAdult);
                    cruisePriceInfo.setPricePortChild(pricePortChild);
                    cruisePriceInfo.setPriceValueAdd2Child(priceValueAdd2Child);
                    cruisePriceInfo.setPriceValueAddChild(priceValueAddChild);
                    cruisePriceInfo.setPriceValueOne(priceValueOne);
                    cruisePriceInfo.setPriceValueTwo(priceValueTwo);
                    cruisePriceInfo.setPriceValueThree(priceValueThree);
                    cruisePriceInfo.setPriceValueFour(priceValueFour);

                    cruisePriceInfos.add(cruisePriceInfo);
                }

                cruisePriceInfoService.add(cruisePriceInfos);
            }
        }
    }

    private Double getPriceValue(Number priceValue)
    {
        return priceValue == null ? 0.0 : priceValue.doubleValue();
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
