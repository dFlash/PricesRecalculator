package net.mpopov.ci.cruise.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.mpopov.ci.common.CurrencyExchangeRateLogUtil;
import net.mpopov.ci.cruise.recalcuator.MinPriceRecalculator;
import net.mpopov.ci.cruise.recalcuator.PriceInfoRecalculator;
import net.mpopov.ci.cruise.recalcuator.Recalculator;

public class MainRecalculator
{

    private static Logger log = Logger.getLogger(MainRecalculator.class);

    public static void main(String[] args)
    {
        log.info("Recalculation started");
        List<Recalculator> recalculators = getRecalculators();
        
        boolean recalculated = false;
        for (Recalculator recalculator : recalculators)
        {
            recalculated |= recalculator.recalculate();
        }
        if (recalculated)
        {
            CurrencyExchangeRateLogUtil ñurrencyExchangeRateLogUtil = new CurrencyExchangeRateLogUtil();
            ñurrencyExchangeRateLogUtil.saveCurrencyExchangeRates();
        }

        log.info("Recalculation finished");
    }
    
    private static List<Recalculator> getRecalculators()
    {
        List<Recalculator> recalculators = new ArrayList<Recalculator>();
        recalculators.add(new MinPriceRecalculator());
        recalculators.add(new PriceInfoRecalculator());
        
        return recalculators;
    }
}
