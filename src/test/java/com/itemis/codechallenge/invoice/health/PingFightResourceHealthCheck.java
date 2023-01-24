package com.itemis.codechallenge.invoice.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import com.itemis.codechallenge.invoice.InvoiceResource;

@Liveness
@ApplicationScoped
public class PingFightResourceHealthCheck implements HealthCheck {

	@Inject
	InvoiceResource invoiceResource; 
	
	@Override
	public HealthCheckResponse call() {
		invoiceResource.hello();
        return HealthCheckResponse.named("Ping Invoice REST Endpoint").up().build();
	}

}

