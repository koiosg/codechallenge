package com.itemis.codechallenge.invoice.entity;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Good {
	
	@NotNull
	public String name;
	
	@NotNull
    @Pattern(regexp = "bookcategory|foodcategory|medicalcategory|musiccategory|perfumcategory", flags = Pattern.Flag.CASE_INSENSITIVE)
	public String goodCategory;
	
	public Boolean isImported;
	
	@NotNull
	@Min(0)
	public Integer quantity;
	
    @Pattern(regexp = "box|bottle|packet", flags = Pattern.Flag.CASE_INSENSITIVE)
	public String unitOfMeasurement;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=3, fraction=2)
	public BigDecimal costPerLineItem;
}
 	