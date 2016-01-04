package net.mpopov.ci.cruise.service;

import java.util.List;

import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;

public interface CurrencyExchangeRateLogService
{
    public void add(CurrencyExchangeRateLog currencyExchangeRate);

    public void save(CurrencyExchangeRateLog currencyExchangeRate);

    public CurrencyExchangeRateLog load(Long currencyId, Short sourceType);

    public List<CurrencyExchangeRateLog> list();

    public void removeAll();
}
