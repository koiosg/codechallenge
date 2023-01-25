package com.itemis.codechallenge.invoice;

import java.io.IOException;

import javax.validation.Valid;

public class InvoiceService {
	
    public static Invoice getInvoice(@Valid Basket basket) throws IOException {
        return new Invoice().buildInvoiceItems(basket).buildSalesTax().buildTotal();
    }
}