package com.itemis.codechallenge.invoice.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class GoodCategory {

	@NotNull
    @Pattern(regexp = "bookcategory|foodcategory|medicalcategory|musiccategory|chocolatecategory|perfumcategory", flags = Pattern.Flag.CASE_INSENSITIVE)
	public String name;
	
	@NotNull
    @Pattern(regexp = "sales_w_o_excempt|sales_with_excempt", flags = Pattern.Flag.CASE_INSENSITIVE)
	public String taxGroup;
}
 	