package net.mpopov.ci.cruise.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "public.cruise_price")
public class CruisePrice
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cruise_price_cruise_price_id_seq")
    @SequenceGenerator(name = "cruise_price_cruise_price_id_seq",
            sequenceName = "cruise_price_cruise_price_id_seq")
    @Column(name = "cruise_price_id", nullable = false)
    private Long cruisePriceId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cruise_id")
    @NotNull
    private Cruise cruise;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cruise_date_range_id")
    @NotNull
    private CruiseDateRange cruiseDateRange;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "currency_id")
    @NotNull
    private Currency currency;

    @Column(name = "date_time")
    @DateTimeFormat(iso = ISO.NONE, pattern = "yyyy-MM-dd H:mm")
    @NotNull
    private Date dateTime;

    /**
     * @return the cruisePriceId
     */
    public Long getCruisePriceId()
    {
        return cruisePriceId;
    }

    /**
     * @param cruisePriceId
     *            the cruisePriceId to set
     */
    public void setCruisePriceId(Long cruisePriceId)
    {
        this.cruisePriceId = cruisePriceId;
    }

    /**
     * @return the cruise
     */
    public Cruise getCruise()
    {
        return cruise;
    }

    /**
     * @param cruise
     *            the cruise to set
     */
    public void setCruise(Cruise cruise)
    {
        this.cruise = cruise;
    }

    /**
     * @return the cruiseDateRange
     */
    public CruiseDateRange getCruiseDateRange()
    {
        return cruiseDateRange;
    }

    /**
     * @param cruiseDateRange
     *            the cruiseDateRange to set
     */
    public void setCruiseDateRange(CruiseDateRange cruiseDateRange)
    {
        this.cruiseDateRange = cruiseDateRange;
    }

    /**
     * @return the currency
     */
    public Currency getCurrency()
    {
        return currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(Currency currency)
    {
        this.currency = currency;
    }

    /**
     * @return the dateTime
     */
    public Date getDateTime()
    {
        return dateTime;
    }

    /**
     * @param dateTime
     *            the dateTime to set
     */
    public void setDateTime(Date dateTime)
    {
        this.dateTime = dateTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "CruisePrice [cruisePriceId=" + cruisePriceId + ", cruise="
                + cruise + ", cruiseDateRange=" + cruiseDateRange
                + ", currency=" + currency + ", dateTime=" + dateTime + "]";
    }

}
