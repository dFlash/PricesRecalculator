package net.mpopov.ci.cruise.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "public.currency_exchange_rate_log")
public class CurrencyExchangeRateLog
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "currency_exchange_rate_log_currency_exchange_rate_id_seq")
    @SequenceGenerator(
            name = "currency_exchange_rate_log_currency_exchange_rate_id_seq",
            sequenceName = "currency_exchange_rate_log_currency_exchange_rate_id_seq")
    @Column(name = "currency_exchange_rate_id", nullable = false)
    private Long currencyExchangeRateLogId;

    @Column(name = "date_time")
    @DateTimeFormat(iso = ISO.NONE, pattern = "yyyy-MM-dd H:mm")
    @NotNull
    private Date dateTime;

    @Column(name = "currency_id")
    @NotNull
    private Long currencyId;

    @Column(name = "rate_value")
    @NotNull
    private Double rateValue;

    @Column(name = "show_site")
    private Boolean showSite;

    @Column(name = "for_currency_id")
    private Long forCurrencyId;

    @Column(name = "source_type")
    @NotNull
    private Short sourceType;

    public Long getCurrencyId()
    {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId)
    {
        this.currencyId = currencyId;
    }

    public Long getCurrencyExchangeRateLogId()
    {
        return currencyExchangeRateLogId;
    }

    public void setCurrencyExchangeRateLogId(Long currencyExchangeRateLogId)
    {
        this.currencyExchangeRateLogId = currencyExchangeRateLogId;
    }

    public Date getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(Date dateTime)
    {
        this.dateTime = dateTime;
    }

    public Double getRateValue()
    {
        return rateValue;
    }

    public void setRateValue(Double rateValue)
    {
        this.rateValue = rateValue;
    }

    public Boolean getShowSite()
    {
        return showSite;
    }

    public void setShowSite(Boolean showSite)
    {
        this.showSite = showSite;
    }

    public Long getForCurrencyId()
    {
        return forCurrencyId;
    }

    public void setForCurrencyId(Long forCurrencyId)
    {
        this.forCurrencyId = forCurrencyId;
    }

    public Short getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(Short sourceType)
    {
        this.sourceType = sourceType;
    }

    @Override
    public String toString()
    {
        return "CurrencyExchangeRateLog [currencyExchangeRateLogId= "
                + currencyExchangeRateLogId + ", dateTime=" + dateTime
                + ", currencyId=" + currencyId + ", rateValue=" + rateValue
                + ", showSite=" + showSite + ", forCurrencyId=" + forCurrencyId
                + ", sourceType=" + sourceType + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((currencyId == null) ? 0 : currencyId.hashCode());
        result = prime * result
                + ((forCurrencyId == null) ? 0 : forCurrencyId.hashCode());
        result = prime * result
                + ((sourceType == null) ? 0 : sourceType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CurrencyExchangeRateLog other = (CurrencyExchangeRateLog) obj;
        if (currencyId == null)
        {
            if (other.currencyId != null)
                return false;
        }
        else if (!currencyId.equals(other.currencyId))
            return false;
        if (forCurrencyId == null)
        {
            if (other.forCurrencyId != null)
                return false;
        }
        else if (!forCurrencyId.equals(other.forCurrencyId))
            return false;
        if (sourceType == null)
        {
            if (other.sourceType != null)
                return false;
        }
        else if (!sourceType.equals(other.sourceType))
            return false;
        return true;
    }

}
