package net.mpopov.ci.cruise.service;

import net.mpopov.ci.cruise.model.CruiseDateRangeMinPriceInfo;

public interface CruiseDateRangeMinPriceInfoService
{

    public void remove(Short sourceType);

    public void add(CruiseDateRangeMinPriceInfo cruiseDateRangeMinPriceInfo);
}
