package com.itemis.codechallenge.invoice.entity;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TaxCategory {
	
	public BigDecimal getTaxpers() {
		return taxpers;
	}

	public void setTaxpers(final BigDecimal taxpers) {
		this.taxpers = taxpers;
	}

	public BigDecimal getExcemptpers() {
		return excemptpers;
	}

	public void setExcemptpers(final BigDecimal excemptpers) {
		this.excemptpers = excemptpers;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
    @Pattern(regexp = "basicsales|additionalsales|basicsalesexcemted|additionalsalesexcempted", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String name;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	@DecimalMax(value = "100.0", inclusive = true)
    @Digits(integer=3, fraction=2)
	private BigDecimal taxpers;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	@DecimalMax(value = "100.0", inclusive = true)
    @Digits(integer=3, fraction=2)
	private BigDecimal excemptpers;
}
 	