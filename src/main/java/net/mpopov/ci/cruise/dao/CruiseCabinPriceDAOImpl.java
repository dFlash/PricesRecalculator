package net.mpopov.ci.cruise.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.mpopov.ci.common.CurrencyUtil.EXCHANGE_RATE_SOURCE_TYPE;
import net.mpopov.ci.cruise.model.CruiseCabinPrice;

@Repository
public class CruiseCabinPriceDAOImpl implements CruiseCabinPriceDAO
{

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<CruiseCabinPrice> list(Short sourceType)
    {
        String hqlTemplate = " from CruiseCabinPrice where "
                + "  cruisePrice.cruiseDateRange.cruise.vessel.company.companyId %s";
        String condition = "";
        if (sourceType.equals(EXCHANGE_RATE_SOURCE_TYPE.CBR.getSourceType()))
        {
            condition = " not in (:excludedCompanyIds)";
        }
        else
        {
            condition = " = :sourceType ";
        }

        String hql = String.format(hqlTemplate, condition);

        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (sourceType.equals(EXCHANGE_RATE_SOURCE_TYPE.CBR.getSourceType()))
        {
            query.setParameterList("excludedCompanyIds",
                    EXCHANGE_RATE_SOURCE_TYPE.getExcludedCbrCompanyIds());
        }
        else
        {
            query.setLong("sourceType", sourceType.longValue());
        }
        List<CruiseCabinPrice> cruiseCabinPrices = query.list();
        return cruiseCabinPrices;
    }

}
