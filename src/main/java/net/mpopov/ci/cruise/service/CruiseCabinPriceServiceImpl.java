package net.mpopov.ci.cruise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mpopov.ci.cruise.dao.CruiseCabinPriceDAO;
import net.mpopov.ci.cruise.model.CruiseCabinPrice;

@Service("cruiseCabinPriceService")
public class CruiseCabinPriceServiceImpl implements CruiseCabinPriceService
{
    @Autowired
    private CruiseCabinPriceDAO cruiseCabinPriceDAO;

    @Transactional
    public List<CruiseCabinPrice> list(Short sourceType)
    {
        return cruiseCabinPriceDAO.list(sourceType);
    }

}
