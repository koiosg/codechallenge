package com.itemis.codechallenge.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class Invoice {
	public List<InvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}

	public void setInvoiceItems(final List<InvoiceItem> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}
	
	public Invoice buildInvoiceItems(final Basket basket) {
		final ArrayList<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
		basket.getGoods().parallelStream().forEach(good -> {
				invoiceItems.add(new InvoiceItem().buildQuantity(good.quantity).
						buildLineItemDescription(good.isImported, good.unitOfMeasurement, good.name).buildLineItemBasicAndAdditionalTax(good.goodCategory, good.costPerLineItem, good.isImported).buildLineItemPrice(good.costPerLineItem));
		});
		this.setInvoiceItems(invoiceItems);
		return this;
	}

	public BigDecimal getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(final BigDecimal salesTax) {
		this.salesTax = salesTax;
	}
	
	public Invoice buildSalesTax() {
		final List<BigDecimal> lineItemTaxAmounts = new ArrayList<BigDecimal>();
		invoiceItems.parallelStream().forEach(invoiceItem-> lineItemTaxAmounts.add(invoiceItem.getLineItemBasicTax().add(invoiceItem.getLineItemImportTax()))); 
		setSalesTax(lineItemTaxAmounts.parallelStream().reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_EVEN));
		return this;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(final BigDecimal total) {
		this.total = total;
	}
	
	public Invoice buildTotal() {
		setTotal(invoiceItems.parallelStream().map(x->x.getLineItemPrice()).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_EVEN));
		return this;
	}

	private List<InvoiceItem> invoiceItems;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=3, fraction=2)
	private BigDecimal salesTax;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=3, fraction=2)
	private BigDecimal total;
}
