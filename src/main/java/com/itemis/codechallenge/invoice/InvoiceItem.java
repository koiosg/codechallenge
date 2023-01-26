package com.itemis.codechallenge.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.itemis.codechallenge.invoice.conf.TaxConfiguration;
import com.itemis.codechallenge.invoice.entity.TaxCategory;

public class InvoiceItem {
	
	private static final String IMPORTED_CATEGORY_SUFFIX = "_import";
	
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

	public InvoiceItem buildLineItemPrice(final BigDecimal costPerLineItem) {
		this.lineItemPrice = costPerLineItem.add(this.lineItemBasicTax).add(this.lineItemImportTax).setScale(2, RoundingMode.HALF_EVEN);
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
		if (null != unitOfMeasurement && !unitOfMeasurement.isEmpty() && !unitOfMeasurement.isBlank()) {
			this.lineItemDescription = this.getLineItemDescription().concat(" ").concat(unitOfMeasurement).concat(" of");
		}
		this.lineItemDescription = this.getLineItemDescription().concat(" ").concat(goodName).concat(": ");
		return this;
	}
	
	public BigDecimal getLineItemBasicTax() {
		return lineItemBasicTax;
	}

	public void setLineItemBasicTax(final BigDecimal lineItemBasicTax) {
		this.lineItemBasicTax = lineItemBasicTax;
	}

	public BigDecimal getLineItemImportTax() {
		return lineItemImportTax;
	}

	public void setLineItemImportTax(BigDecimal lineItemImportTax) {
		this.lineItemImportTax = lineItemImportTax;
	}

	public InvoiceItem buildLineItemBasicAndAdditionalTax(final String goodCategory, final BigDecimal costPerLineItem, final Boolean isImported) {
		final String taxGroupName = TaxConfiguration.
				goodCategories.stream().filter
				(goodCat->goodCat.name.equals(isImported ? goodCategory.concat(IMPORTED_CATEGORY_SUFFIX) : goodCategory)).
				findAny().
				orElseThrow().
				taxGroup;
		final List<String> taxCategoriesPerGroup = TaxConfiguration.taxGroups.stream().filter(taxConf -> taxConf.getName().equals(taxGroupName)).findFirst().get().getTaxCategories();
		final String basicTaxCategoryName = taxCategoriesPerGroup.get(0);
		TaxCategory basicTaxCategory = TaxConfiguration.getTaxCategories().stream().filter(taxCat->taxCat.getName().equals(basicTaxCategoryName)).findFirst().get();
		final BigDecimal basicTaxPersPerCategory = 0 < basicTaxCategory.getExcemptpers().longValueExact() ? 
				basicTaxCategory.getTaxpers().divide(BigDecimal.valueOf(100)).add(basicTaxCategory.getTaxpers().divide(basicTaxCategory.getExcemptpers()).negate()) : basicTaxCategory.getTaxpers().divide(BigDecimal.valueOf(100));
		this.lineItemBasicTax = costPerLineItem.multiply(basicTaxPersPerCategory);
		final String additionalTaxCategoryName = taxCategoriesPerGroup.get(1);
		TaxCategory additionalTaxCategory = TaxConfiguration.getTaxCategories().stream().filter(taxCat->taxCat.getName().equals(additionalTaxCategoryName)).findFirst().get();
		final BigDecimal additionalTaxPersPerCategory = 0 < additionalTaxCategory.getExcemptpers().longValueExact() ? 
				additionalTaxCategory.getTaxpers().divide(BigDecimal.valueOf(100)).add(additionalTaxCategory.getTaxpers().divide(additionalTaxCategory.getExcemptpers()).negate()) : additionalTaxCategory.getTaxpers().divide(BigDecimal.valueOf(100));
		this.lineItemImportTax = costPerLineItem.multiply(additionalTaxPersPerCategory);
		return this;
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
	
	private String lineItemDescription = "";
}
