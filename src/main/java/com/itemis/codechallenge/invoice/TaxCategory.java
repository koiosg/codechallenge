package com.itemis.codechallenge.invoice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TaxCategory {
	
	@NotNull
    @Pattern(regexp = "sales_w_o_excempt|sales_with_excempt", flags = Pattern.Flag.CASE_INSENSITIVE)
	public String name;
	
	@NotNull
	public String taxGroup;
	
}
 	