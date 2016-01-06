package net.mpopov.ci.cruise.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.mpopov.ci.common.CurrencyUtil.EXCHANGE_RATE_SOURCE_TYPE;
import net.mpopov.ci.cruise.model.CruiseDateRangeMinPrice;

@Repository
public class CruiseDateRangeMinPriceDAOImpl
        implements CruiseDateRangeMinPriceDAO
{

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<CruiseDateRangeMinPrice> list(Short sourceType)
    {
        String hqlTemplate = " from CruiseDateRangeMinPrice where "
                + "  cruiseDateRange.cruise.vessel.company.companyId %s";
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
        List<CruiseDateRangeMinPrice> minPricesForSourceType = query.list();
        return minPricesForSourceType;
    }

}
