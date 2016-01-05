package net.mpopov.ci.cruise.dao;

import java.util.List;

import net.mpopov.ci.cruise.model.CruiseCabinPrice;

public interface CruiseCabinPriceDAO
{
    public List<CruiseCabinPrice> list(Short sourceType);
}
