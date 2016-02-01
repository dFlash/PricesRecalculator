package net.mpopov.ci.cruise.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "public.cruise_date_range_min_price_info")
public class CruiseDateRangeMinPriceInfo implements Serializable
{
    private static final long serialVersionUID = 8521386199879324557L;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cruise_date_range_id")
    @NotNull
    private CruiseDateRange cruiseDateRange;

    @Id
    @Column(name = "currency_id")
    @NotNull
    private Long currencyId;

    @Column(name = "price_value")
    @NotNull
    private Double priceValue;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cruise_date_range_min_price_id")
    @NotNull
    private CruiseDateRangeMinPrice cruiseDateRangeMinPrice;

    public CruiseDateRange getCruiseDateRange()
    {
        return cruiseDateRange;
    }

    public void setCruiseDateRange(CruiseDateRange cruiseDateRange)
    {
        this.cruiseDateRange = cruiseDateRange;
    }

    public Long getCurrencyId()
    {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId)
    {
        this.currencyId = currencyId;
    }

    public Double getPriceValue()
    {
        return priceValue;
    }

    public void setPriceValue(Double priceValue)
    {
        this.priceValue = priceValue;
    }

    public CruiseDateRangeMinPrice getCruiseDateRangeMinPrice()
    {
        return cruiseDateRangeMinPrice;
    }

    public void setCruiseDateRangeMinPrice(
            CruiseDateRangeMinPrice cruiseDateRangeMinPrice)
    {
        this.cruiseDateRangeMinPrice = cruiseDateRangeMinPrice;
    }

    @Override
    public String toString()
    {
        return "CruiseDateRangeMinPriceInfo [cruiseDateRange=" + cruiseDateRange
                + ", currencyId=" + currencyId + ", priceValue=" + priceValue
                + ", cruiseDateRangeMinPrice=" + cruiseDateRangeMinPrice + "]";
    }

}
