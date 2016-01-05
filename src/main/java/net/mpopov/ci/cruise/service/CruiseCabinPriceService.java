package net.mpopov.ci.cruise.service;

import java.util.List;

import net.mpopov.ci.cruise.model.CruiseCabinPrice;

public interface CruiseCabinPriceService
{
    public List<CruiseCabinPrice> list(Short sourceType);
}
