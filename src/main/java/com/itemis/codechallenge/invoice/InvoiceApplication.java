package com.itemis.codechallenge.invoice;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(title = "Invoice API",
        description = "This API returns Invoice details",
        version = "1.0",
        contact = @Contact(name = "itemis_code_challenge")),
    servers = {
        @Server(url = "http://localhost:8083")
    }
)
public class InvoiceApplication extends Application {
}