package com.itemis.codechallenge.invoice.health;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import com.itemis.codechallenge.invoice.Invoice;
import com.itemis.codechallenge.invoice.InvoiceService;

@Readiness
@ApplicationScoped
public class DatabaseConnectionHealthCheck implements HealthCheck {

	@Inject 
	InvoiceService invoiceService;
	
	@Override
	public HealthCheckResponse call() {
		HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Datasource connection health check");
		
		try {
			List <Invoice> invoices = new ArrayList<Invoice>();
			invoices.add(new Invoice());
			responseBuilder.withData("Invoice details number", invoices.size()).up();
        } catch (IllegalStateException e) {
            responseBuilder.down();
        }
        return responseBuilder.build();			
	}
}
