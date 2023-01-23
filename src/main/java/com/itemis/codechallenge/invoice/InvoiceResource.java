package com.itemis.codechallenge.invoice;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

@Path("/api/invoice")
@Produces(APPLICATION_JSON)
public class InvoiceResource {

    private static final Logger LOGGER = Logger.getLogger(InvoiceResource.class);

    @Inject
    InvoiceService service;

    @Operation(summary = "Fetch Invoice details for a basket")
    @APIResponse(responseCode = "200", description = "Invoice details", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Invoice.class)))
    @GET
    public Response getInvoice(
        @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Basket.class)))
        @Valid Basket basket) {
        Invoice invoice = service.getInvoice(basket);
        LOGGER.debug("Invoice fetched for a basket " + basket);
        return Response.ok(invoice).build();
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/hello")
    public String hello() {
        return "hello";
    }
}