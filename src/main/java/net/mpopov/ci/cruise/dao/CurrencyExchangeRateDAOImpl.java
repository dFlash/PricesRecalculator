package net.mpopov.ci.cruise.dao;

import java.util.List;

import net.mpopov.ci.cruise.model.CurrencyExchangeRate;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("currencyExchangeRateDAO")
public class CurrencyExchangeRateDAOImpl implements CurrencyExchangeRateDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<CurrencyExchangeRate> list()
    {
        String hql = "from CurrencyExchangeRate currencyExchangeRate ";

        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        List<CurrencyExchangeRate> list = query.list();

        return list;
    }

}
