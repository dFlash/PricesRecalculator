package net.mpopov.ci.cruise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mpopov.ci.cruise.dao.CurrencyExchangeRateLogDAO;
import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;

@Service("currencyExchangeRateLogService")
public class CurrencyExchangeRateLogServiceImpl
        implements CurrencyExchangeRateLogService
{

    @Autowired
    private CurrencyExchangeRateLogDAO currencyExchangeRateLogDAO;

    @Transactional
    public void add(CurrencyExchangeRateLog currencyExchangeRateLog)
    {
        currencyExchangeRateLogDAO.add(currencyExchangeRateLog);

    }

    @Transactional
    public List<CurrencyExchangeRateLog> list()
    {
        return currencyExchangeRateLogDAO.list();
    }

    @Transactional
    public void removeAll()
    {
        currencyExchangeRateLogDAO.removeAll();
    }

}
