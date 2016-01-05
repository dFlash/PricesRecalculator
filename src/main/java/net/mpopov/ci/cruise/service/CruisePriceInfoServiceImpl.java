package net.mpopov.ci.cruise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mpopov.ci.cruise.dao.CruisePriceInfoDAO;
import net.mpopov.ci.cruise.model.CruisePriceInfo;

@Service("cruisePriceInfoService")
public class CruisePriceInfoServiceImpl implements CruisePriceInfoService
{

    @Autowired
    private CruisePriceInfoDAO cruisePriceInfoDAO;

    @Transactional
    public void remove(Short sourceType)
    {
        cruisePriceInfoDAO.remove(cruisePriceInfoDAO
                .listCruiseDateRangeIds(sourceType));
        
    }

    @Transactional
    public void add(CruisePriceInfo cruisePriceInfo)
    {
        cruisePriceInfoDAO.add(cruisePriceInfo);
        
    }

}
