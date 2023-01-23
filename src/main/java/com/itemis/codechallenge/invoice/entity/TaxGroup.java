package com.itemis.codechallenge.invoice.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TaxGroup {
	
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<String> getTaxCategories() {
		return taxCategories;
	}

	public void setTaxCategories(final List<String> taxCategories) {
		this.taxCategories = taxCategories;
	}
	
	@NotNull
    @Pattern(regexp = "sales_w_o_excempt|sales_with_excempt", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String name;
	
	@NotNull
    @Pattern(regexp = "basicsales|additionalsales", flags = Pattern.Flag.CASE_INSENSITIVE)
	List<String> taxCategories;
}
 	