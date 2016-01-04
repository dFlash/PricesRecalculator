package net.mpopov.ci.cruise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mpopov.ci.cruise.dao.CruiseDateRangeMinPriceInfoDAO;
import net.mpopov.ci.cruise.model.CruiseDateRangeMinPriceInfo;

@Service("cruiseDateRangeMinPriceInfoService")
public class CruiseDateRangeMinPriceInfoServiceImpl implements CruiseDateRangeMinPriceInfoService
{
    
    @Autowired
    private CruiseDateRangeMinPriceInfoDAO cruiseDateRangeMinPriceInfoDAO;

    @Transactional
    public void remove(Short sourceType)
    {
        cruiseDateRangeMinPriceInfoDAO.remove(cruiseDateRangeMinPriceInfoDAO
                .listCruiseDateRangeIds(sourceType));
    }

    @Transactional
    public void add(CruiseDateRangeMinPriceInfo cruiseDateRangeMinPriceInfo)
    {
        cruiseDateRangeMinPriceInfoDAO.add(cruiseDateRangeMinPriceInfo);
        
    }

}
