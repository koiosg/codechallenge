package com.itemis.codechallenge.invoice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.MatchResult;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonParseException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;

import com.itemis.codechallenge.invoice.conf.TaxConfiguration;
import com.itemis.codechallenge.invoice.entity.Good;
import com.itemis.codechallenge.invoice.entity.GoodCategory;
import com.itemis.codechallenge.invoice.entity.TaxCategory;
import com.itemis.codechallenge.invoice.entity.TaxGroup;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceConfigurationTest {
	
    @Inject
    Validator validator;
    
    @Inject
    InvoiceService invoiceService;

    @Test
    public void testGoodCreationNOK() {
    	// creating a good
    	final Good good= new Good();
    	good.name = "eqwqtwqwefewfewfwfew";
    	good.goodCategory = "ABCDC";
        Set<ConstraintViolation<Good>> violations = validator.validate(good);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testGoodCreationOK() {
    	// creating a good
    	final Good good= new Good();
    	good.name = "eqwqtwqt";
    	good.goodCategory = "perfumcategory";
    	good.quantity = 1;
    	good.costPerLineItem = BigDecimal.ONE;
        Set<ConstraintViolation<Good>> violations = validator.validate(good);
        assertTrue(violations.isEmpty());
    }
    
	@NotNull
    @Pattern(regexp = "sales_w_o_excempt|sales_with_excempt", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String name;
	
	@NotNull
    @Pattern(regexp = "basicsales|additionalsales", flags = Pattern.Flag.CASE_INSENSITIVE)
	List<String> taxCategories;
    
    @Test
    public void testTaxGroupCreationWrongNameNOK() {
    	// creating a good
    	final TaxGroup taxGroup = new TaxGroup();
    	taxGroup.setName("sales_with__ABC");
    	taxGroup.setTaxCategories(new ArrayList<String>());
        Set<ConstraintViolation<TaxGroup>> violations = validator.validate(taxGroup);
        assertFalse(violations.isEmpty());
    }
    
    @Test
    public void testTaxGroupCreationEmptyListNOK() {
    	// creating a good
    	final TaxGroup taxGroup = new TaxGroup();
    	taxGroup.setName("sales_w_o_excempt");
    	taxGroup.setTaxCategories(null);
        Set<ConstraintViolation<TaxGroup>> violations = validator.validate(taxGroup);
        assertFalse(violations.isEmpty());
    }
    
    @Test
    public void taxGroupsTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<TaxGroup> taxGroups = TaxConfiguration.getTaxGroups();
        assertNotNull(taxGroups);
        assertTrue(0 < taxGroups.size());
    }
    
    @Test
    public void taxCategoriesTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<TaxCategory> taxCategories = TaxConfiguration.getTaxCategories();
        assertNotNull(taxCategories);
        assertTrue(0 < taxCategories.size());
    }
    
    @Test
    public void goodsCategoriesTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<GoodCategory> goodCategories = TaxConfiguration.getGoodCategories();
        assertNotNull(goodCategories);
        assertTrue(1 < goodCategories.size());
        
    }
    
    @Test
    public void goodsTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<Good> goods = TaxConfiguration.getGoods();
        assertNotNull(goods);
        assertTrue(0 < goods.size());
    }

    @Test
    public void testEmptyBasketCreationNOK() {
    	// creating a basket
    	final Basket basket = new Basket();
        assertNull(basket.getGoods());
    }

    @Test 
    @Disabled
    void testRegex() {
    	Scanner lineItemScanner = new Scanner("> 1 book at 12.49");
    	lineItemScanner.useDelimiter("$");
    	String regexp = "^>\\p{Space}+(\\p{Digit}+)\\p{Space}+"
    			+ "([\\p{Alnum}|\\p{Space}]*)\\p{Space}*([\\p{Alnum}|\\p{Space}]+)\\p{Space}*"
    			+ "at\\p{Space}+(\\p{Digit}+[.]\\p{Digit}+)$";
    	if (lineItemScanner.hasNext(regexp))
    		lineItemScanner.next(regexp);
    	MatchResult matcher = lineItemScanner.match();
    	System.out.println(matcher.group(0));
    	System.out.println(matcher.group(1));
    	String productSpec = matcher.group(2);
    	System.out.println(productSpec);
    	System.out.println(matcher.group(3));
    	System.out.println(matcher.group(4));
    	if (productSpec.contains("imported")) {
        	System.out.println("Good is imported");
    	}
    	lineItemScanner.close();
    	String UoM = "";
    	if (productSpec.matches(".*box.*")) {
    		UoM = "box";
    	} else if (productSpec.matches(".*bottle.*")) {
    		UoM = "bottle";
    	} else if (productSpec.matches(".*packet.*")) {
    		UoM = "packet";
    	} 
    	System.out.println("UoM = " + UoM);
    }
    
    @Test
    void testNonEmptyBasketOk() throws IOException {
    		// read ascii file
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
			do {
    			String lineItem = sc.nextLine();
    			while (patternInput.matcher(lineItem).find() && sc.hasNextLine() || patternInputN.matcher(lineItem).find() && goodList.isEmpty()) {
    				lineItem = sc.nextLine();
    			}
    			if (patternInputN.matcher(lineItem).find() && !goodList.isEmpty() && sc.hasNextLine()) {
    		        basket.setGoods(goodList);
    		        assertNotNull(invoiceService.getInvoice(basket));
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
    		sc.close();
	        basket.setGoods(goodList);
	        assertNotNull(invoiceService.getInvoice(basket));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
//    	Good good= new Good();
//    	good.name = "book";
//    	good.goodCategory = "bookcategory";
//    	good.quantity = 1;
//    	good.costPerLineItem = BigDecimal.TEN;
//        Basket basket = new Basket();
//        basket.setGoods(Arrays.asList(new Good[] {good}));
//        assertNotNull(InvoiceService.getInvoice(basket));
    }
}