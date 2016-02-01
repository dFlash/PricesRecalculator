package net.mpopov.ci.common;

import java.util.List;

import net.mpopov.ci.cruise.model.CurrencyExchangeRate;
import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;
import net.mpopov.ci.cruise.service.CurrencyExchangeRateLogService;
import net.mpopov.ci.cruise.service.CurrencyExchangeRateService;

public class CurrencyExchangeRateLogUtil extends ContextAdapter
{
    public void saveCurrencyExchangeRates()
    {
        CurrencyExchangeRateLogService currencyExchangeRateLogService = getBean(
                "currencyExchangeRateLogService");
        currencyExchangeRateLogService.removeAll();

        CurrencyExchangeRateService currencyExchangeRateService = getBean(
                "currencyExchangeRateService");
        List<CurrencyExchangeRate> currencyExchangeRates = currencyExchangeRateService
                .list();
        for (CurrencyExchangeRate currencyExchangeRate : currencyExchangeRates)
        {
            CurrencyExchangeRateLog currencyExchangeRateLog = createCurrencyExchangeRateLog(
                    currencyExchangeRate);
            currencyExchangeRateLogService.add(currencyExchangeRateLog);
        }
    }

    private CurrencyExchangeRateLog createCurrencyExchangeRateLog(
            CurrencyExchangeRate currencyExchangeRate)
    {
        CurrencyExchangeRateLog currencyExchangeRateLog = new CurrencyExchangeRateLog();
        currencyExchangeRateLog.setCurrencyExchangeRateLogId(
                currencyExchangeRate.getCurrencyExchangeRateId());
        currencyExchangeRateLog
                .setCurrencyId(currencyExchangeRate.getCurrencyId());
        currencyExchangeRateLog.setDateTime(currencyExchangeRate.getDateTime());
        currencyExchangeRateLog
                .setForCurrencyId(currencyExchangeRate.getForCurrencyId());
        currencyExchangeRateLog
                .setRateValue(currencyExchangeRate.getRateValue());
        currencyExchangeRateLog
                .setSourceType(currencyExchangeRate.getSourceType());

        return currencyExchangeRateLog;
    }

}
