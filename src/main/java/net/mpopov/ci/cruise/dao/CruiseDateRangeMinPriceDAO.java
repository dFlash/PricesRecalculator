package net.mpopov.ci.cruise.dao;

import java.util.List;

import net.mpopov.ci.cruise.model.CruiseDateRangeMinPrice;

public interface CruiseDateRangeMinPriceDAO
{
    public List<CruiseDateRangeMinPrice> list(Short sourceType);

}
