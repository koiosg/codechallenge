package com.itemis.codechallenge.invoice;

import java.io.IOException;
import java.math.BigDecimal;

import javax.inject.Inject;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonParseException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;

import com.itemis.codechallenge.invoice.conf.TaxConfiguration;
import com.itemis.codechallenge.invoice.entity.TaxCategory;
import com.itemis.codechallenge.invoice.entity.TaxGroup;

public class InvoiceItem {
	
    @Inject
    TaxConfiguration taxConfiguration;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}
	
	public InvoiceItem buildQuantity(final Integer quantity) {
		this.quantity = quantity;
		return this;
	}

	public BigDecimal getLineItemPrice() {
		return lineItemPrice;
	}

	public void setLineItemPrice(final BigDecimal lineItemPrice) {
		this.lineItemPrice = lineItemPrice;
	}

	public InvoiceItem buildLineItemPrice(final BigDecimal lineItemPrice) {
		this.lineItemPrice = lineItemPrice;
		return this;
	}
	
	public String getLineItemDescription() {
		return lineItemDescription;
	}

	public void setLineItemDescription(final String lineItemDescription) {
		this.lineItemDescription = lineItemDescription;
	}

	public InvoiceItem buildLineItemDescription(final Boolean isImported, final String unitOfMeasurement, final String goodName) {
		this.lineItemDescription = this.lineItemDescription.concat(this.quantity.toString());
		if (Boolean.TRUE.equals(isImported)) {
			this.lineItemDescription = this.getLineItemDescription().concat(" imported");
		} 
		if (null != unitOfMeasurement) {
			this.lineItemDescription = this.getLineItemDescription().concat(unitOfMeasurement).concat(" of");
		}
		this.lineItemDescription = this.getLineItemDescription().concat(" ").concat(goodName);
		return this;
	}
	
	public BigDecimal getLineItemBasicTax() {
		return lineItemBasicTax;
	}

	public void setLineItemBasicTax(final BigDecimal lineItemBasicTax) {
		this.lineItemBasicTax = lineItemBasicTax;
	}

	public InvoiceItem buildLineItemBasicTax(final String goodCategory, final BigDecimal costPerLineItem) throws JsonParseException, JsonMappingException, IOException {
		final TaxGroup taxGroup = taxConfiguration.getTaxGroups().stream().filter(taxConf -> taxConf.getName().equals(taxConfiguration.getGoodsCategories().stream().filter(x->x.name.equals(goodCategory)).findFirst().get().taxGroup)).findFirst().get();
		TaxCategory taxCategory = taxConfiguration.getTaxCategories().stream().filter(x->x.getName().equals(taxGroup.getTaxCategories().get(0))).findFirst().get();
		final BigDecimal taxPersPerCategory = taxCategory.getExcemptpers().longValueExact() > 0 ? 
				taxCategory.getTaxpers().divide(BigDecimal.valueOf(100)).multiply(taxCategory.getExcemptpers().divide(BigDecimal.valueOf(100))) : taxCategory.getTaxpers().divide(BigDecimal.valueOf(100));
		this.lineItemBasicTax = costPerLineItem.multiply(taxPersPerCategory);
		return this;
	}
	
	public BigDecimal getLineItemImportTax() {
		return lineItemImportTax;
	}

	public void setLineItemImportTax(final BigDecimal lineItemImportTax) {
		this.lineItemImportTax = lineItemImportTax;
	}

	@NotNull
	@Min(0)
	private Integer quantity;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=3, fraction=2)
	private BigDecimal lineItemBasicTax;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=3, fraction=2)
	private BigDecimal lineItemImportTax;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=3, fraction=2)
	private BigDecimal lineItemPrice;
	
	private String lineItemDescription;
}
