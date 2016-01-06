package net.mpopov.ci.cruise.service;

import java.util.List;

import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;

public interface CurrencyExchangeRateLogService
{
    public void add(CurrencyExchangeRateLog currencyExchangeRate);

    public List<CurrencyExchangeRateLog> list();

    public void removeAll();
}
