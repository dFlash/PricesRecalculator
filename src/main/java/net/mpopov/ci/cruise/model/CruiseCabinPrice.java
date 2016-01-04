package net.mpopov.ci.cruise.model;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "public.cruise_cabin_price")
public class CruiseCabinPrice
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cruise_cabin_price_cruise_cabin_price_id_seq")
    @SequenceGenerator(name = "cruise_cabin_price_cruise_cabin_price_id_seq",
            sequenceName = "cruise_cabin_price_cruise_cabin_price_id_seq")
    @Column(name = "cruise_cabin_price_id", nullable = false)
    private Long cruiseCabinPriceId;

    @Column(name = "cabin_id")
    @NotNull
    private Long cabinId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cruise_price_id")
    @NotNull
    private CruisePrice cruisePrice;

    @Column(name = "price_value")
    @NotNull
    @Min(1)
    @Max(100000)
    private Integer priceValue;

    @Column(name = "price_value_one")
    private Integer priceValueOne;

    @Column(name = "price_value_two")
    private Integer priceValueTwo;

    @Column(name = "price_value_three")
    private Integer priceValueThree;

    @Column(name = "price_value_four")
    private Integer priceValueFour;

    @Column(name = "price_value_add_c")
    private Integer priceValueAddChild;

    @Column(name = "price_value_add_cc")
    private Integer priceValueAdd2Child;

    @Column(name = "price_port_adult")
    @NotNull
    private Integer pricePortAdult;

    @Column(name = "price_port_child")
    @NotNull
    private Integer pricePortChild;

    public Integer getPriceValueOne() {
		return priceValueOne;
	}

	public void setPriceValueOne(Integer priceValueOne) {
		this.priceValueOne = priceValueOne;
	}

	public Integer getPriceValueTwo() {
		return priceValueTwo;
	}

	public void setPriceValueTwo(Integer priceValueTwo) {
		this.priceValueTwo = priceValueTwo;
	}

	public Integer getPriceValueThree() {
		return priceValueThree;
	}

	public void setPriceValueThree(Integer priceValueThree) {
		this.priceValueThree = priceValueThree;
	}

	public Integer getPriceValueFour() {
		return priceValueFour;
	}

	public void setPriceValueFour(Integer priceValueFour) {
		this.priceValueFour = priceValueFour;
	}

	public Integer getPriceValueAddChild() {
		return priceValueAddChild;
	}

	public void setPriceValueAddChild(Integer priceValueAddChild) {
		this.priceValueAddChild = priceValueAddChild;
	}

	public Integer getPriceValueAdd2Child() {
		return priceValueAdd2Child;
	}

	public void setPriceValueAdd2Child(Integer priceValueAdd2Child) {
		this.priceValueAdd2Child = priceValueAdd2Child;
	}

	public Integer getPricePortAdult() {
		return pricePortAdult;
	}

	public void setPricePortAdult(Integer pricePortAdult) {
		this.pricePortAdult = pricePortAdult;
	}

	public Integer getPricePortChild() {
		return pricePortChild;
	}

	public void setPricePortChild(Integer pricePortChild) {
		this.pricePortChild = pricePortChild;
	}

	/**
     * @return the cruiseCabinPriceId
     */
    public Long getCruiseCabinPriceId()
    {
        return cruiseCabinPriceId;
    }

    /**
     * @param cruiseCabinPriceId
     *            the cruiseCabinPriceId to set
     */
    public void setCruiseCabinPriceId(Long cruiseCabinPriceId)
    {
        this.cruiseCabinPriceId = cruiseCabinPriceId;
    }



    public Long getCabinId()
    {
        return cabinId;
    }

    public void setCabinId(Long cabinId)
    {
        this.cabinId = cabinId;
    }

    /**
     * @return the cruisePrice
     */
    public CruisePrice getCruisePrice()
    {
        return cruisePrice;
    }

    /**
     * @param cruisePrice
     *            the cruisePrice to set
     */
    public void setCruisePrice(CruisePrice cruisePrice)
    {
        this.cruisePrice = cruisePrice;
    }

    /**
     * @return the priceValue
     */
    public Integer getPriceValue()
    {
        return priceValue;
    }

    /**
     * @param priceValue
     *            the priceValue to set
     */
    public void setPriceValue(Integer priceValue)
    {
        this.priceValue = priceValue;
    }

	@Override
	public String toString() {
		return "CruiseCabinPrice [cruiseCabinPriceId=" + cruiseCabinPriceId
				+ ", cabinId=" + cabinId + ", cruisePrice=" + cruisePrice
				+ ", priceValue=" + priceValue + ", priceValueOne="
				+ priceValueOne + ", priceValueTwo=" + priceValueTwo
				+ ", priceValueThree=" + priceValueThree + ", priceValueFour="
				+ priceValueFour + ", priceValueAddChild=" + priceValueAddChild
				+ ", priceValueAdd2Child=" + priceValueAdd2Child
				+ ", pricePortAdult=" + pricePortAdult + ", pricePortChild="
				+ pricePortChild + "]";
	}

}
