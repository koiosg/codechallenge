package com.itemis.codechallenge.invoice;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.itemis.codechallenge.invoice.entity.Good;

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
    void shouldNotSendInvalidBasketData() {
        Basket emptyBasket = new Basket();
        given()
            .body(emptyBasket)
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .header(ACCEPT, APPLICATION_JSON)
            .when()
            .get("/api/invoice")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());
    }
    
    @Test
    void shouldSendValidEmptyBasketDataNOK() {
        Basket emptyBasket = new Basket();
        given()
            .body(emptyBasket)
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .header(ACCEPT, APPLICATION_JSON)
            .when()
            .get("/api/invoice")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());
    }
    
    @Test
    void shouldSendValidNonEmptyBasketData() {
    	Good good= new Good();
    	good.name = "book";
    	good.goodCategory = "bookcategory";
    	good.quantity = 1;
    	good.costPerLineItem = BigDecimal.valueOf(12.49);
    	good.isImported = Boolean.FALSE;
        Basket basket = new Basket();
        basket.setGoods(Arrays.asList(new Good[] {good}));
        given()
            .body(basket)
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .header(ACCEPT, APPLICATION_JSON)
            .when()
            .get("/api/invoice")
            .then()
            .statusCode(OK.getStatusCode()).log().all();    
    }
    
    

    @Test
    void shouldPingOpenAPI() {
        given()
            .header(ACCEPT, APPLICATION_JSON)
            .when().get("/q/openapi")
            .then()
            .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingSwaggerUI() {
        given()
            .when().get("/q/swagger-ui")
            .then()
            .statusCode(OK.getStatusCode());
    }
    
    @Test
    void shouldPingLiveness() {
        given()
            .when().get("/q/health/live")
            .then()
            .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingReadiness() {
        given()
            .when().get("/q/health/ready")
            .then()
            .statusCode(OK.getStatusCode());
    }
    
    @Test
    void shouldPingMetrics() {
        given()
            .header(ACCEPT, TEXT_PLAIN)
            .when().get("/q/metrics")
            .then()
            .statusCode(OK.getStatusCode());
    }
}