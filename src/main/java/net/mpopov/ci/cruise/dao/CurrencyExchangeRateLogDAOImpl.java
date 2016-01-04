package net.mpopov.ci.cruise.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.mpopov.ci.cruise.model.CurrencyExchangeRateLog;

@Repository
public class CurrencyExchangeRateLogDAOImpl implements CurrencyExchangeRateLogDAO
{
    
    @Autowired
    private SessionFactory sessionFactory;

    public void add(CurrencyExchangeRateLog currencyExchangeRateLog)
    {
        sessionFactory.getCurrentSession().save(currencyExchangeRateLog);
    }

    public void save(CurrencyExchangeRateLog currencyExchangeRate)
    {
        // TODO Auto-generated method stub
        
    }

    public CurrencyExchangeRateLog load(Long currencyId, Short sourceType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<CurrencyExchangeRateLog> list()
    {
        String hql = "from CurrencyExchangeRateLog currencyExchangeRateLog ";

        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        List<CurrencyExchangeRateLog> list = query.list();

        return list;
    }

    public void removeAll()
    {
        String hql = "delete from CurrencyExchangeRateLog ";

        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        query.executeUpdate();
    }

}
