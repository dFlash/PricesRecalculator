package net.mpopov.ci.cruise.service;

import java.util.List;

import net.mpopov.ci.cruise.model.CruiseDateRangeMinPriceInfo;

public interface CruiseDateRangeMinPriceInfoService
{

    public void remove(Short sourceType);

    public void add(CruiseDateRangeMinPriceInfo cruiseDateRangeMinPriceInfo);

    public void add(
            List<CruiseDateRangeMinPriceInfo> cruiseDateRangeMinPriceInfos);
}
