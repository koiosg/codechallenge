package com.itemis.codechallenge.invoice;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;


@ApplicationScoped
public class InvoiceService {
	
	@Inject
	Invoice invoice;
	
    public Invoice getInvoice(@Valid Basket basket) throws IOException {
        return invoice.buildInvoiceItems(basket).buildSalesTax().buildTotal();
    }
}