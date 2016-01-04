package net.mpopov.ci.cruise.dao;

import java.util.List;

import net.mpopov.ci.cruise.model.CruiseDateRangeMinPriceInfo;

public interface CruiseDateRangeMinPriceInfoDAO
{

    public void remove(List<Long> cruiseDateRangeIds);

    public void add(CruiseDateRangeMinPriceInfo cruiseDateRangeMinPriceInfo);

    public List<Long> listCruiseDateRangeIds(Short sourceType);
}
