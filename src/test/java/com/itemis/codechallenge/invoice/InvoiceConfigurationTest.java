package com.itemis.codechallenge.invoice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    TaxConfiguration taxConfiguration;

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
    	good.goodCategory = "bookcategory";
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
    	final List<TaxGroup> taxGroups = taxConfiguration.getTaxGroups();
        assertNotNull(taxGroups);
        assertTrue(0 < taxGroups.size());
    }
    
    @Test
    public void taxCategoriesTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<TaxCategory> taxCategories = taxConfiguration.getTaxCategories();
        assertNotNull(taxCategories);
        assertTrue(0 < taxCategories.size());
    }
    
    @Test
    public void goodsCategoriesTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<GoodCategory> goodCategories = taxConfiguration.getGoodsCategories();
        assertNotNull(goodCategories);
        assertTrue(0 < goodCategories.size());
    }
    
    @Test
    public void goodsTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<Good> goods = taxConfiguration.getGoods();
        assertNotNull(goods);
        assertTrue(0 < goods.size());
    }

    @Test
    public void testBasketCreationNOK() {
    	// creating a basket
    	final Basket emptyBasket = new Basket();
        assertNull(emptyBasket.getGoods());
    }

    @Test
    public void testBasketCreationOK() {
    	// creating a basket
    	final Basket basket = new Basket();
    	basket.setGoods(new ArrayList<Good>());
        assertNotNull(basket.getGoods());
    }
}