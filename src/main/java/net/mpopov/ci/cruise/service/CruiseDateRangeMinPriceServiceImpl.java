package net.mpopov.ci.cruise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mpopov.ci.cruise.dao.CruiseDateRangeMinPriceDAO;
import net.mpopov.ci.cruise.model.CruiseDateRangeMinPrice;

@Service("cruiseDateRangeMinPriceService")
public class CruiseDateRangeMinPriceServiceImpl
        implements CruiseDateRangeMinPriceService
{

    @Autowired
    private CruiseDateRangeMinPriceDAO cruiseDateRangeMinPriceDAO;

    @Transactional
    public List<CruiseDateRangeMinPrice> list(Short sourceType)
    {
        return cruiseDateRangeMinPriceDAO.list(sourceType);
    }

}
