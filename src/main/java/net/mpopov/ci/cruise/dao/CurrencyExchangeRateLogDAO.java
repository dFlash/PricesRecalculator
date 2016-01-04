package net.mpopov.ci.cruise.dao;

import java.util.List;

import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;

public interface CurrencyExchangeRateLogDAO
{
    public void add(CurrencyExchangeRateLog currencyExchangeRate);

    public void save(CurrencyExchangeRateLog currencyExchangeRate);

    public CurrencyExchangeRateLog load(Long currencyId, Short sourceType);

    public List<CurrencyExchangeRateLog> list();

    public void removeAll();
}
