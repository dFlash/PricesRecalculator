package net.mpopov.ci.cruise.service;

import java.util.List;

import net.mpopov.ci.cruise.model.CruisePriceInfo;

public interface CruisePriceInfoService
{
    public void remove(Short sourceType);

    public void add(CruisePriceInfo cruisePriceInfo);

    public void add(List<CruisePriceInfo> cruisePriceInfos);
}
