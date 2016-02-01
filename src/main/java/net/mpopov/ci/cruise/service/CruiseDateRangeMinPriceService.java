package net.mpopov.ci.cruise.service;

import java.util.List;

import net.mpopov.ci.cruise.model.CruiseDateRangeMinPrice;

public interface CruiseDateRangeMinPriceService
{

    public List<CruiseDateRangeMinPrice> list(Short sourceType);
}
