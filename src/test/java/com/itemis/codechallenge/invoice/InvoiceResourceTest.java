package com.itemis.codechallenge.invoice;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.itemis.codechallenge.invoice.conf.TaxConfiguration;
import com.itemis.codechallenge.invoice.entity.Good;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

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
            .statusCode(OK.getStatusCode());    
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
    
    @Test
    void loadAndRunInputFileAsPerCodeChallenge() {
    	try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("inputs.txt")) {
    		Scanner sc = new Scanner(inputStream);
    		java.util.regex.Pattern patternInput = java.util.regex.Pattern.compile("### INPUT:");
    		java.util.regex.Pattern patternInputN = java.util.regex.Pattern.compile("Input \\d+:");
			List<Good> goodList = new ArrayList<Good>();
	    	Basket basket = new Basket();
	    	if (!sc.hasNextLine()) {
	    		sc.close();
	    		return;
	    	}
	    	Response response = null;
	    	Integer basketCount = 1;
			System.out.println("### OUTPUT");
	    	do {
    			String lineItem = sc.nextLine();
    			while (patternInput.matcher(lineItem).find() && sc.hasNextLine() || patternInputN.matcher(lineItem).find() && goodList.isEmpty()) {
    				lineItem = sc.nextLine();
    			}
    			if (patternInputN.matcher(lineItem).find() && !goodList.isEmpty() && sc.hasNextLine()) {
    		        basket.setGoods(goodList);
    		        given()
    	            .body(basket)
    	            .header(CONTENT_TYPE, APPLICATION_JSON)
    	            .header(ACCEPT, APPLICATION_JSON)
    	            .when()
    	            .get("/api/invoice")
    	            .then()
    	            .statusCode(OK.getStatusCode());
    		        response = given()
    	            .body(basket)
    	            .header(CONTENT_TYPE, APPLICATION_JSON)
    	            .header(ACCEPT, APPLICATION_JSON)
    	            .when()
    	            .get("/api/invoice");
    				System.out.println("Output " + basketCount++ + ":");
    				System.out.println();
    		        //convert JSON to string
    		        JsonPath jsonString = new JsonPath(response.asString());
    		        int listSize = jsonString.getInt("invoiceItems.size()");
    		        for(int i = 0; i < listSize; i++) {
    		        	System.out.println("> " + jsonString.getString("invoiceItems["+i+"].lineItemDescription") + " " + BigDecimal.valueOf(jsonString.getDouble("invoiceItems["+i+"].lineItemPrice")).setScale(2));    		              		        	
    		        }
    		        System.out.println("> Sales Taxes: " + BigDecimal.valueOf(jsonString.getDouble("salesTax")).setScale(2));
    		        System.out.println("> Total: " + BigDecimal.valueOf(jsonString.getDouble("total")).setScale(2));
    		        System.out.println();
    		        goodList = new ArrayList<Good>();
    		        basket = new Basket();
    				lineItem = sc.nextLine();
    			}
		    	Scanner lineItemScanner = new Scanner(lineItem);
		    	lineItemScanner.useDelimiter("$");
		    	String regexp = "^>\\p{Space}+(\\p{Digit}+)\\p{Space}+"
		    			+ "([\\p{Alnum}|\\p{Space}]+)\\p{Space}*"
		    			+ "at\\p{Space}+(\\p{Digit}+[.]\\p{Digit}+)$";
		    	if (lineItemScanner.hasNext(regexp))
	    		lineItemScanner.next(regexp);
		    	MatchResult matcher = lineItemScanner.match();
		    	String quantity = matcher.group(1);
		    	String productSpec = matcher.group(2);
		    	List<String> lst = Arrays.asList(productSpec.split("\\p{Space}"));
		    	String goodName = (0 < lst.indexOf("of")) ? String.join(" ", lst.subList(lst.indexOf("of") + 1, lst.size())).replace("imported", "").trim() : productSpec.trim(); 
    		    String cost = matcher.group(3);
		    	List<Good> taxConfigGoods = TaxConfiguration.getGoods();
		    	String goodCategory = taxConfigGoods.stream().filter(x->x.name.toString().equalsIgnoreCase(goodName.toString())).findFirst().orElseThrow().goodCategory;
		    	lineItemScanner.close();
		    	Good good = new Good().buildQuantity(Integer.valueOf(quantity)).buildName(goodName).buildcostPerLineItem(BigDecimal.
		    			valueOf(Double.parseDouble(cost))).buildIsImported(1 < lst.size() && productSpec.contains("imported") ? Boolean.TRUE : Boolean.FALSE).buildCategory(goodCategory);
		    	String UoM = "";
		    	if (productSpec.matches(".*box.*")) {
		    		UoM = "box";
		    	} else if (productSpec.matches(".*bottle.*")) {
		    		UoM = "bottle";
		    	} else if (productSpec.matches(".*packet.*")) {
		    		UoM = "packet";
		    	} 
		    	good.buildUnitOfMeasurement(UoM);
		    	goodList.add(good);
    		} while (sc.hasNextLine());
	        basket.setGoods(goodList);
	        response = given()
    	            .body(basket)
    	            .header(CONTENT_TYPE, APPLICATION_JSON)
    	            .header(ACCEPT, APPLICATION_JSON)
    	            .when()
    	            .get("/api/invoice");
    		System.out.println("Output " + basketCount + ":");
    		System.out.println();
    		//convert JSON to string
    		JsonPath jsonString = new JsonPath(response.asString());
    		int listSize = jsonString.getInt("invoiceItems.size()");
    		for(int i = 0; i < listSize; i++) {
    			System.out.println("> " + jsonString.getString("invoiceItems["+i+"].lineItemDescription") + " " + BigDecimal.valueOf(jsonString.getDouble("invoiceItems["+i+"].lineItemPrice")).setScale(2));    		              		        	
    		}
            System.out.println("> Sales Taxes: " + BigDecimal.valueOf(jsonString.getDouble("salesTax")).setScale(2));
 	        System.out.println("> Total: " + BigDecimal.valueOf(jsonString.getDouble("total")).setScale(2));    
    		sc.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}