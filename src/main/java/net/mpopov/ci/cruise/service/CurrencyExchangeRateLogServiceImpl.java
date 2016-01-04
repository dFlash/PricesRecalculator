package net.mpopov.ci.cruise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mpopov.ci.cruise.dao.CurrencyExchangeRateLogDAO;
import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;

@Service("currencyExchangeRateLogService")
public class CurrencyExchangeRateLogServiceImpl implements CurrencyExchangeRateLogService
{

    @Autowired
    private CurrencyExchangeRateLogDAO currencyExchangeRateLogDAO;

    @Transactional
    public void add(CurrencyExchangeRateLog currencyExchangeRate)
    {
        // TODO Auto-generated method stub
        
    }

    @Transactional
    public void save(CurrencyExchangeRateLog currencyExchangeRate)
    {
        // TODO Auto-generated method stub
        
    }

    @Transactional
    public CurrencyExchangeRateLog load(Long currencyId, Short sourceType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Transactional
    public List<CurrencyExchangeRateLog> list()
    {
        return currencyExchangeRateLogDAO.list();
    }

    public void removeAll()
    {
        // TODO Auto-generated method stub
        
    }

}
