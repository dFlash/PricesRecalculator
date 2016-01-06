package net.mpopov.ci.cruise.dao;

import java.util.List;

import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;

public interface CurrencyExchangeRateLogDAO
{
    public void add(CurrencyExchangeRateLog currencyExchangeRate);

    public List<CurrencyExchangeRateLog> list();

    public void removeAll();
}
