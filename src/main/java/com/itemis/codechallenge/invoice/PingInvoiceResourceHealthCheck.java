package com.itemis.codechallenge.invoice;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class PingInvoiceResourceHealthCheck implements HealthCheck {

    @Inject
    InvoiceResource invoiceResource;

    @Override
    public HealthCheckResponse call() {
        invoiceResource.hello();
        return HealthCheckResponse.named("Ping Invoice REST Endpoint").up().build();
    }
}


