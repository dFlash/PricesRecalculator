package net.mpopov.ci.cruise.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.mpopov.ci.common.CurrencyUtil.EXCHANGE_RATE_SOURCE_TYPE;
import net.mpopov.ci.cruise.model.CruisePriceInfo;

@Repository
public class CruisePriceInfoDAOImpl implements CruisePriceInfoDAO
{

    @Autowired
    private SessionFactory sessionFactory;

    public void add(CruisePriceInfo cruisePriceInfo)
    {
        sessionFactory.getCurrentSession().save(cruisePriceInfo);
    }

    public void remove(List<Long> cruiseDateRangeIds)
    {
        if (!cruiseDateRangeIds.isEmpty())
        {
            String hql = " delete from CruisePriceInfo  " 
                    + " where "
                    + " cruiseDateRange.cruiseDateRangeId in (:ñruiseDateRangeIds)";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setParameterList("ñruiseDateRangeIds", cruiseDateRangeIds);
            query.executeUpdate();
        }

    }

    @SuppressWarnings("unchecked")
    public List<Long> listCruiseDateRangeIds(Short sourceType)
    {
        String hqlTemplate = " select cruiseDateRange.cruiseDateRangeId from CruiseDateRange cruiseDateRange "
                + " where "
                + " cruiseDateRange.cruise.vessel.company.companyId %s";
        String condition = "";
        if (sourceType.equals(EXCHANGE_RATE_SOURCE_TYPE.CBR.getSourceType()))
        {
            condition = " not in (:excludedCompanyIds) ";
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
        List<Long> cruiseDateRangeIds = query.list();
        return cruiseDateRangeIds;
    }

}
