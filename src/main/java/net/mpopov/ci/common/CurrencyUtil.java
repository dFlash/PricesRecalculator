package net.mpopov.ci.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mpopov.ci.cruise.model.CurrencyExchangeRate;

public class CurrencyUtil
{

    public static enum EXCHANGE_RATE_SOURCE_TYPE
    {
        MSC((short) 27, "MSC"), CBR((short) 1, "CBR");

        private Short sourceType;

        private String name;

        private EXCHANGE_RATE_SOURCE_TYPE(Short sourceType, String name)
        {
            this.sourceType = sourceType;
            this.name = name;
        }

        public Short getSourceType()
        {
            return sourceType;
        }

        public String getName()
        {
            return name;
        }

        public static EXCHANGE_RATE_SOURCE_TYPE getSourceTypeByCompanyId(
                Long companyId)
        {
            if (companyId.equals(MSC.getSourceType()))
            {
                return MSC;
            }
            else
            {
                return CBR;
            }
        }

        public static List<Long> getExcludedCbrCompanyIds()
        {
            List<Long> excludedCbrCompanyIds = new ArrayList<Long>();
            for (EXCHANGE_RATE_SOURCE_TYPE type : EXCHANGE_RATE_SOURCE_TYPE
                    .values())
            {
                if (type != EXCHANGE_RATE_SOURCE_TYPE.CBR)
                {
                    excludedCbrCompanyIds.add(type.getSourceType().longValue());
                }
            }
            return excludedCbrCompanyIds;
        }

    }

    private static final char COMMA_SEPARATOR = ',';

    public static double getCurrencyRateValue(String valueString)
            throws ParseException
    {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(COMMA_SEPARATOR);
        df.setDecimalFormatSymbols(symbols);
        double currencyRateValue = (Double) df.parse(valueString);

        return currencyRateValue;
    }

    /**
     * Retrieving prices for all currencies
     * 
     * @param currency
     *            - base currency (from admin pane)
     * @param price
     *            - price in base currency
     * @param currencyExchangeRates
     *            - exchange rates for cruise
     * @return collection with pairs (key - currency id, value - corresponding
     *         price)
     */
    public static Map<Long, Double> recalculatePriceValues(Long currencyId,
            Double price, List<CurrencyExchangeRate> currencyExchangeRates)
    {
        Map<Long, Double> pricesByCurrencies = new HashMap<Long, Double>();
        pricesByCurrencies.put(currencyId, price);
        Double baseCurrencyRate = null;
        for (CurrencyExchangeRate rate : currencyExchangeRates)
        {
            if (rate.getCurrencyId().equals(currencyId))
            {
                baseCurrencyRate = rate.getRateValue();
                break;
            }
        }

        for (CurrencyExchangeRate rate : currencyExchangeRates)
        {
            if (!rate.getCurrencyId().equals(currencyId))
            {
                Double currPrice = price * baseCurrencyRate
                        / rate.getRateValue();
                pricesByCurrencies.put(rate.getCurrencyId(), currPrice);
            }
        }

        return pricesByCurrencies;
    }

}
