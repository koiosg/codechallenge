package com.itemis.codechallenge.invoice;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
            .when().get("/api/invoice/hello")
            .then()
            .statusCode(200)
            .body(is("hello"));
    }

    @Test
    void shouldNotReturnInvalidInvoiceData() {
        Basket basket = new Basket();

        given()
            .body(basket)
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .header(ACCEPT, APPLICATION_JSON)
            .when()
            .post("/api/invoice")
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    void shouldPingOpenAPI() {
        given()
            .header(ACCEPT, APPLICATION_JSON)
            .when().get("/openapi")
            .then()
            .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingSwaggerUI() {
        given()
            .when().get("/swagger-ui")
            .then()
            .statusCode(OK.getStatusCode());
    }
    
    @Test
    void shouldPingLiveness() {
        given()
            .when().get("/health/live")
            .then()
            .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingReadiness() {
        given()
            .when().get("/health/ready")
            .then()
            .statusCode(OK.getStatusCode());
    }
    
    @Test
    void shouldPingMetrics() {
        given()
            .header(ACCEPT, APPLICATION_JSON)
            .when().get("/metrics/application")
            .then()
            .statusCode(OK.getStatusCode());
    }
}