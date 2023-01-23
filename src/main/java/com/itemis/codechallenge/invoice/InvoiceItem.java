package com.itemis.codechallenge.invoice;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class InvoiceItem {
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
	public BigDecimal price;
	
	public String toString() {
		return null;
	}
}
