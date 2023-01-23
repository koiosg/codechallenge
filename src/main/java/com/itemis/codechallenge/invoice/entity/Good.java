package com.itemis.codechallenge.invoice.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Good {
	
	@NotNull
	public String name;
	
	@NotNull
    @Pattern(regexp = "bookcategory|foodcategory|medicalcategory|musiccategory|chocolatecategory|perfumcategory", flags = Pattern.Flag.CASE_INSENSITIVE)
	public String goodCategory;
}
 	