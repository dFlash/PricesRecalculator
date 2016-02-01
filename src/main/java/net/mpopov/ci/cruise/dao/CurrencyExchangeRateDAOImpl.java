package net.mpopov.ci.cruise.dao;

import java.util.ArrayList;
import java.util.List;

import net.mpopov.ci.cruise.model.CurrencyExchangeRate;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("currencyExchangeRateDAO")
public class CurrencyExchangeRateDAOImpl implements CurrencyExchangeRateDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    public List<CurrencyExchangeRate> list()
    {
        List<CurrencyExchangeRate> exchangeRates = new ArrayList<CurrencyExchangeRate>();
        String hql = "select currencyExchangeRate from CurrencyExchangeRate currencyExchangeRate                                        "
                + " where (currencyExchangeRate.sourceType, currencyExchangeRate.currencyId, currencyExchangeRate.dateTime)             "
                + "   IN (select currencyExchangeRate.sourceType, currencyExchangeRate.currencyId, max(currencyExchangeRate.dateTime)   "
                + "       from CurrencyExchangeRate currencyExchangeRate                                                                "
                + "       group by currencyExchangeRate.sourceType, currencyExchangeRate.currencyId )";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
        while (results.next())
        {
            CurrencyExchangeRate currencyExchangeRate = (CurrencyExchangeRate) results
                    .get(0);
            exchangeRates.add(currencyExchangeRate);
        }
        results.close();

        return exchangeRates;
    }

}
