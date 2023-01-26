package com.itemis.codechallenge.invoice.entity;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Good {
	
	public Good buildName(final String goodName) {
		this.name = goodName;
		return this;
	}
	
	@NotNull
	public String name;
	
	@NotNull
    @Pattern(regexp = "bookcategory|foodcategory|medicalcategory|musiccategory|perfumcategory", flags = Pattern.Flag.CASE_INSENSITIVE)
	public String goodCategory;
	
	public Good buildCategory(final String goodCategory) {
		this.goodCategory = goodCategory;
		return this;
	}
		
	// default value is FALSE
	public Boolean isImported = Boolean.FALSE;
	
	public Good buildIsImported(final Boolean isImported) {
		this.isImported = isImported;
		return this;
	}
	
	@NotNull
	@Min(0)
	public Integer quantity;
	
	public Good buildQuantity(final Integer quantity) {
		this.quantity = quantity;
		return this;
	}
	
    @Pattern(regexp = "box|bottle|packet", flags = Pattern.Flag.CASE_INSENSITIVE)
	public String unitOfMeasurement;
    
	public Good buildUnitOfMeasurement(final String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
		return this;
	}
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=3, fraction=2)
	public BigDecimal costPerLineItem;
	
	public Good buildcostPerLineItem(final BigDecimal costPerLineItem) {
		this.costPerLineItem = costPerLineItem;
		return this;
	}
}
 	