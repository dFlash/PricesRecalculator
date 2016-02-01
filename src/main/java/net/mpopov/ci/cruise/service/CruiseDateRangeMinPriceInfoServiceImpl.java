package net.mpopov.ci.cruise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mpopov.ci.cruise.dao.CruiseDateRangeMinPriceInfoDAO;
import net.mpopov.ci.cruise.model.CruiseDateRangeMinPriceInfo;

@Service("cruiseDateRangeMinPriceInfoService")
public class CruiseDateRangeMinPriceInfoServiceImpl
        implements CruiseDateRangeMinPriceInfoService
{

    @Autowired
    private CruiseDateRangeMinPriceInfoDAO cruiseDateRangeMinPriceInfoDAO;

    @Transactional
    public void remove(Short sourceType)
    {
        List<Long> listCruiseDateRangeIds = cruiseDateRangeMinPriceInfoDAO
                .listCruiseDateRangeIds(sourceType);
        cruiseDateRangeMinPriceInfoDAO.remove(listCruiseDateRangeIds);
    }

    @Transactional
    public void add(CruiseDateRangeMinPriceInfo cruiseDateRangeMinPriceInfo)
    {
        cruiseDateRangeMinPriceInfoDAO.add(cruiseDateRangeMinPriceInfo);

    }

    @Transactional
    public void add(
            List<CruiseDateRangeMinPriceInfo> cruiseDateRangeMinPriceInfos)
    {
        for (CruiseDateRangeMinPriceInfo cruiseDateRangeMinPriceInfo : cruiseDateRangeMinPriceInfos)
        {
            cruiseDateRangeMinPriceInfoDAO.add(cruiseDateRangeMinPriceInfo);
        }
    }

}
