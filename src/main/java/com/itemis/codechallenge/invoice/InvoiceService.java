package com.itemis.codechallenge.invoice;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

@ApplicationScoped
public class InvoiceService {
	
    public Invoice getInvoice(@Valid Basket basket) {
        Invoice inv = this.calculate(basket);
        return inv;
    }

	private Invoice calculate(@Valid Basket invoice) {
		return null;
	}
}