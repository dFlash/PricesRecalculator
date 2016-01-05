package net.mpopov.ci.cruise.dao;

import java.util.List;

import net.mpopov.ci.cruise.model.CruisePriceInfo;

public interface CruisePriceInfoDAO
{

    public void add(CruisePriceInfo cruisePriceInfo);

    public void remove(List<Long> cruiseDateRangeIds);

    public List<Long> listCruiseDateRangeIds(Short sourceType);
}
