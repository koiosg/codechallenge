package com.itemis.codechallenge.invoice.health;

import java.io.IOException;
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
import com.itemis.codechallenge.invoice.conf.TaxConfiguration;
import com.itemis.codechallenge.invoice.entity.TaxGroup;

@Readiness
@ApplicationScoped
public class ConfigurationHealthCheck implements HealthCheck {

	@Inject 
	InvoiceService invoiceService;
	
	@Inject
	TaxConfiguration taxConfiguration;
	
	@Override
	public HealthCheckResponse call() {
		HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Datasource connection health check");
		
		try {
			List <TaxGroup> taxGroups = taxConfiguration.getTaxGroups();
			responseBuilder.withData("TaxGroups config elements list size", taxGroups.size()).up();
        } catch (IllegalStateException | IOException e) {
            responseBuilder.down();
        }
        return responseBuilder.build();			
	}
}
